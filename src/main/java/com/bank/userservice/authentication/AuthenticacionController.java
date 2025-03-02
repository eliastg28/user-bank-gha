package com.bank.userservice.authentication;

import com.bank.userservice.authentication.dto.LoginRequest;
import com.bank.userservice.authentication.dto.LoginResponse;
import com.bank.userservice.common.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthenticacionController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Endpoint para iniciar sesión y obtener un token JWT.
     *
     * @param loginRequest Datos de login (usuario y contraseña).
     * @return Un token JWT en caso de éxito.
     */
    @PostMapping("/login")
    public Mono<ResponseEntity<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        return authenticationService.autenticar(loginRequest)
                .map(jwt -> ResponseEntity.ok(new LoginResponse(jwt))) // Si el token es generado correctamente, se retorna un ResponseEntity 200 OK con el token
                .onErrorResume(CustomException.class, e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null))) // Si hay un error, devolvemos un ResponseEntity con status 401
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)); // En caso de que no haya respuesta del servicio, retornamos un BAD_REQUEST
    }
}
