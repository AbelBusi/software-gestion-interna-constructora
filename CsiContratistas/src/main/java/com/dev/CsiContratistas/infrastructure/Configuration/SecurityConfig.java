package com.dev.CsiContratistas.infrastructure.Configuration;

import com.dev.CsiContratistas.infrastructure.jwt.JpaUserDetailsService;
import com.dev.CsiContratistas.infrastructure.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JpaUserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(withDefaults()) // Habilita CORS usando la configuración global
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/favicon.ico",          //  <-- aquí
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        //Controlador de las apis
                        .requestMatchers("/autenticacion/login").permitAll()
                        .requestMatchers("/autenticacion/empleado").permitAll()
                        .requestMatchers("/autenticacion/cambiarClaveUsuario").permitAll()

                        //Este es para los controladores frontend
                        .requestMatchers("/js/**", "/css/**","/img/**","/modelos3d/**").permitAll()
                        .requestMatchers("/administrador/ingresar").permitAll()
                        .requestMatchers("/administrador/recuperar-clave").permitAll()
                        .requestMatchers("/administrador/menu/**").permitAll()
                        .requestMatchers("/administrador/inicio").permitAll()
                        .requestMatchers("/administrador/usuarios/**").permitAll()
                        .requestMatchers("/administrador/roles").permitAll()
                        .requestMatchers("/administrador/roles/**").permitAll()
                        .requestMatchers("/administrador/materiales").permitAll()
                        .requestMatchers("/administrador/materiales/**").permitAll()
                        .requestMatchers("/administrador/empleados").permitAll()
                        .requestMatchers("/administrador/empleados/**").permitAll()
                        .requestMatchers("/administrador/profesiones").permitAll()
                        .requestMatchers("/administrador/profesiones/**").permitAll()
                        .requestMatchers("/administrador/ramas").permitAll()
                        .requestMatchers("/administrador/ramas/**").permitAll()
                        .requestMatchers("/verificarEmail/**").permitAll()
                        .requestMatchers("/error").permitAll()

                        //LandinOage
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/api/v1/**").authenticated()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Configuración explícita de CORS para localhost:3000
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://csi-contratistas.vercel.app","https://csi-contratistas-git-implementacion-astro-borogeuses-projects.vercel.app/","https://csi-contratistas-git-testingdeploy-borogeuses-projects.vercel.app/","https://csi-contratistas-git-testing-borogeuses-projects.vercel.app/"));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH"));


        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
