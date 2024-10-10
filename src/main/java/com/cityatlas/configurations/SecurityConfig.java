package com.cityatlas.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Konstruktor för att injicera JwtAuthenticationFilter.
     *
     * @param jwtAuthenticationFilter - Filtern som hanterar JWT-autentisering i varje begäran.
     */
    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Konfigurerar säkerhetsinställningarna för HTTP-begäranden i applikationen.
     * Här inaktiveras CSRF-skydd, CORS aktiveras med standardinställningar, och olika
     * säkerhetsregler tillämpas på olika URL-mönster.
     *
     * @param httpSecurity - Objekt som tillåter konfiguration av webbaserad säkerhet för specifika HTTP-begäranden.
     * @return En SecurityFilterChain som specificerar säkerhetskonfigurationen.
     * @throws Exception - Om det uppstår något fel under konfigurationen.
     */
@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())  // Inaktivera CSRF-skydd eftersom sessioner inte används
                .cors(withDefaults())           // Aktivera CORS med standardinställningar
                .authorizeHttpRequests(auth -> {
                    // Tillåt alla att komma åt /auth/**-vägar utan autentisering
                    auth.requestMatchers("/auth/**").permitAll();
                    // Tillåt alla att komma åt Swagger UI
                    auth.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll();
                    // Tillåt användare med roller USER eller ADMIN att komma åt /user/**
                    auth.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN");
                    // Tillåt endast användare med rollen ADMIN att komma åt /admin/**
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    // Kräver autentisering för alla övriga begäranden
                    auth.anyRequest().authenticated();
                })
                // Ange att sessioner inte skapas (STATELESS)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Lägg till JWT-autentiseringsfiltret före UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    /**
     * Konfigurerar CORS-filter för att tillåta specifika domäner, metoder och headers.
     * Detta möjliggör att klienter från andra domäner kan skicka HTTP-begäranden till servern.
     *
     * @return CorsFilter som hanterar CORS-konfigurationen för alla inkommande begäranden.
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // Tillåt bara begäranden från http://localhost:8080
        config.setAllowedOrigins(List.of("http://localhost:8080"));
        // Tillåt specifika HTTP-metoder
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        // Tillåt headers som Authorization och Content-Type
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // Skapa en källa som matchar alla URL-mönster (/**) med ovanstående konfiguration
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

}
