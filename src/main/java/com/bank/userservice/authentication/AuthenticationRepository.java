package com.bank.userservice.authentication;

import com.bank.userservice.common.model.Usuario;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AuthenticationRepository extends ReactiveCrudRepository<Usuario, String> {

    Mono<Usuario> findByEmail(String email);
}
