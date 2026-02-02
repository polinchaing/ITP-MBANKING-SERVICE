package co.istad.polin.admin_bff.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain webSecurity(ServerHttpSecurity http){



        http.authorizeExchange(exchange -> exchange
                // pathMatcher used like requestMatcher
                // if we use gateway have security and microservice authenticated can't put pathMatcher
                // put requestMatcher in microservice also gateway permit all
//                .pathMatchers("account/public/**").permitAll()
                .anyExchange().authenticated()
        );

        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);
//        disable Logout
//        http.logout(ServerHttpSecurity.LogoutSpec::disable);
        http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);

//        http.oauth2ResourceServer(oauth2->oauth2
//                        .jwt(Customizer.withDefaults())
//                );

        http.oauth2Login(Customizer.withDefaults());

        return http.build();
    }

}
