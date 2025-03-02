package com.bank.userservice.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // Deshabilitamos CSRF (Cross-Site Request Forgery)
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated() // El resto de las rutas requieren autenticación
                .and()
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class); // Añadimos nuestro filtro de autenticación
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Utilizamos BCrypt para encriptar las contraseñas
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        // Implementar un servicio que busque al usuario en la base de datos y lo cargue
        return username -> {
            // Aquí debes buscar al usuario en tu base de datos o repositorio
            // Este es solo un ejemplo para evitar errores
            if ("user@example.com".equals(username)) {
                return new User("user@example.com", passwordEncoder().encode("password"),
                        new ArrayList<>()); // Puedes agregar los roles del usuario aquí
            } else {
                throw new UsernameNotFoundException("Usuario no encontrado");
            }
        };
    }
}
