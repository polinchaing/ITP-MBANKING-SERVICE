package co.istad.polin.pipeline_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity){
        httpSecurity.authorizeHttpRequests(request->request
                .anyRequest().permitAll()
        );

//        httpSecurity.httpBasic(basic->basic.disable());
//        httpSecurity.oauth2ResourceServer(oauth2ResourceServer->
//                oauth2ResourceServer.jwt(Customizer.withDefaults()));
        return httpSecurity.build();
    }

}
