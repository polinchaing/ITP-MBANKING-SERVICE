package co.istad.polin.gateway_server.security;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityWebFilterChain webSecurity(ServerHttpSecurity http){
//
//                // pathMatcher used like requestMatcher
//                // if we use gateway have security and microservice authenticated can't put pathMatcher
//                // put requestMatcher in microservice also gateway permit all
////                .pathMatchers("account/public/**").permitAll()
//                http.authorizeExchange(exchanges -> exchanges
//                        .pathMatchers("/account/public/**").permitAll()
//                        .anyExchange().authenticated());
//
//        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
//        http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);
//        http.logout(ServerHttpSecurity.LogoutSpec::disable);
//        http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);
//
//        http.oauth2ResourceServer(oauth2->oauth2
//                        .jwt(Customizer.withDefaults())
//                );
//
////        http.oauth2Login(Customizer.withDefaults());
//
//        return http.build();
//    }
//
//}
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

// config with webFlux
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain webSecurity (ServerHttpSecurity http) {

        http.authorizeExchange(exchange -> exchange
                .anyExchange().permitAll());

        // Method reference
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);
        http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);
        http.logout(ServerHttpSecurity.LogoutSpec::disable);

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
//        http.oauth2Login(Customizer.withDefaults());

        return http.build();
    }
}

