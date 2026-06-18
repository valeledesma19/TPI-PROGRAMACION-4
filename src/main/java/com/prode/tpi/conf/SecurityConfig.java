package com.prode.tpi.conf;

import com.prode.tpi.feature.auth.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm
                        -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/Fecha/Listar").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/Fecha/Crear").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/Fecha/Modificar/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/Fecha/Eliminar/**").hasRole("ADMIN")
                .requestMatchers("/api/Fecha/**").permitAll()
                .requestMatchers("/api/equipos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/ranking/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/partidos/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/partidos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/partidos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/partidos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/partidos/**").hasRole("ADMIN")
                .requestMatchers("/api/pronosticos/**").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/api/estadisticas/**").hasRole("USER")
                .requestMatchers(HttpMethod.PUT, "/api/Partido/ActualizarEstado/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://localhost:5174"
        ));

        configuration.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
