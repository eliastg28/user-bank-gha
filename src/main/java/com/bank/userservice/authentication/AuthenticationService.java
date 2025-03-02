package com.bank.userservice.authentication;

import com.bank.userservice.authentication.dto.LoginRequest;
import com.bank.userservice.common.exception.CustomException;
import com.bank.userservice.authentication.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Método para autenticar al usuario y generar un token JWT.
     *
     * @param loginRequest Contiene las credenciales del usuario.
     * @return Token JWT si las credenciales son válidas.
     */
    public Mono<String> autenticar(LoginRequest loginRequest) {
        return authenticationRepository.findByEmail(loginRequest.getEmail())
                .switchIfEmpty(Mono.error(new CustomException("Usuario no encontrado"))) // Si el usuario no existe, lanzamos error
                .flatMap(usuario -> {
                    if (passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) { // Verificamos la contraseña
                        // Generamos el token reactivo, basado en el usuario encontrado
                        return Mono.just(jwtUtil.generarToken(usuario)); // Usamos el usuario completo para la generación del token
                    } else {
                        return Mono.error(new CustomException("Credenciales inválidas")); // Si la contraseña no coincide, lanzamos error
                    }
                });
    }
}
