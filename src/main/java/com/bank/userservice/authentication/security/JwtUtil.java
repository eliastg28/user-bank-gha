package com.bank.userservice.authentication.security;

import com.bank.userservice.common.model.Usuario;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime; // en milisegundos

    /**
     * Genera un token JWT para el usuario proporcionado.
     *
     * @param usuario El usuario para el cual se genera el token.
     * @return El token JWT generado.
     */
    public String generarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail()) // Obtener el email del usuario
                .claim("roles", usuario.getRoles())  // Obtener los roles del usuario
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Valida el token JWT.
     *
     * @param token El token JWT a validar.
     * @return true si el token es válido, de lo contrario, false.
     */
    public boolean validarToken(String token) {
        try {
            // Usamos parserBuilder() para crear el JwtParser y parseClaimsJws() correctamente
            JwtParser parser = Jwts.parser()
                    .setSigningKey(secretKey)  // Usa tu clave secreta
                    .build();

            // Validar el token y obtener los claims
            Jws<Claims> claimsJws = parser.parseClaimsJws(token);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false; // Si algo sale mal, el token no es válido
        }
    }

    /**
     * Obtiene el nombre de usuario (subject) del token JWT.
     *
     * @param token El token JWT.
     * @return El nombre de usuario del token.
     */
    public String obtenerUsernameDelToken(String token) {
        return obtenerClaimDelToken(token, Claims::getSubject);
    }

    /**
     * Obtiene los roles del token JWT.
     *
     * @param token El token JWT.
     * @return Los roles del token.
     */
    public String obtenerRolesDelToken(String token) {
        return obtenerClaimDelToken(token, claims -> (String) claims.get("roles"));
    }

    /**
     * Método genérico para obtener un claim del token JWT.
     *
     * @param token El token JWT.
     * @param claimsResolver Una función que extrae el claim del token.
     * @param <T> El tipo del claim.
     * @return El valor del claim.
     */
    private <T> T obtenerClaimDelToken(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = obtenerClaimsDelToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Obtiene los claims del token JWT.
     *
     * @param token El token JWT.
     * @return Los claims del token.
     */
    private Claims obtenerClaimsDelToken(String token) {
        JwtParser parser = Jwts.parser()
                .setSigningKey(secretKey)  // Usa tu clave secreta
                .build();

        // Analiza el token JWT y obtiene los claims
        return parser.parseClaimsJws(token).getBody();
    }
}
