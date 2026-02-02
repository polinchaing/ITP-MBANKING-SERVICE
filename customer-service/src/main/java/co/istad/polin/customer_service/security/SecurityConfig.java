package co.istad.polin.customer_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){

        http.authorizeHttpRequests(request->request
                .anyRequest().authenticated()
        );

        http.csrf(token-> token.disable());
        http.httpBasic(basic->basic.disable());
        http.formLogin(form-> form.disable());

        http.oauth2ResourceServer(oauth2ResourceServer->
                oauth2ResourceServer.jwt(Customizer.withDefaults()));

        http.sessionManagement(session->session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return http.build();
    }

}
