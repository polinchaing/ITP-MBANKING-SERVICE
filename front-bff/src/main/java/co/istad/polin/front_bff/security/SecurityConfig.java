package co.istad.polin.front_bff.security;

//import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;

import java.net.URI;

//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//    @Value("${app.frontend-gateway.url:http://localhost:3000}")
//    private String frontendUrl;
//
//    @Bean
//    public SecurityWebFilterChain webSecurity(ServerHttpSecurity http) {
//
//        http.authorizeExchange(exchanges -> exchanges
//                .pathMatchers("/account/public/**",
//                        "/",
//                        "/_next/**",
//                        "/favicon.ico",
//                        "/images/**",
//                        "/RTR-LOGO.png",
//                        "/robots.txt"
//                ).permitAll()
//                .pathMatchers("/data/**").authenticated()
//
//                .anyExchange().authenticated());
//
//        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
//        http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);
//        http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);
//
//        http.oauth2ResourceServer(auth2 ->  auth2
//                .jwt(Customizer.withDefaults()));
//        http.oauth2Login(auth2 -> auth2
//                .authenticationSuccessHandler(
//                        new RedirectServerAuthenticationSuccessHandler(frontendUrl + "/")
//                ));
//
//        RedirectServerLogoutSuccessHandler logoutSuccessHandler = new RedirectServerLogoutSuccessHandler();
//        logoutSuccessHandler.setLogoutSuccessUrl(URI.create(frontendUrl + "/"));
//
//        // Customize Logout
//        http.logout(logout -> logout
//                .logoutUrl("/logout")
//                .logoutSuccessHandler(
//                        logoutSuccessHandler
//                ));
//
//        return http.build();
//    }
//
//
//
//}

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

//import java.net.URI;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain webSecurity(ServerHttpSecurity http,
                                              ReactiveClientRegistrationRepository clientRegistrationRepository) {

        http.authorizeExchange(exchange -> exchange
                .pathMatchers("/data/**").authenticated()
                .anyExchange().permitAll()
        );

        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);
        //http.logout(ServerHttpSecurity.LogoutSpec::disable);
        http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);

        http.oauth2Login(Customizer.withDefaults());

        // customize logout
        http.logout(logoutSpec -> logoutSpec
                .logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository))
        );

        return http.build();
    }


    // OIDC logout
    private ServerLogoutSuccessHandler oidcLogoutSuccessHandler(ReactiveClientRegistrationRepository clientRegistrationRepository) {
        OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository);
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");

        return oidcLogoutSuccessHandler;
    }

    // Client logout
    private ServerLogoutSuccessHandler serverLogoutSuccessHandler() {

        RedirectServerLogoutSuccessHandler redirectServerLogoutSuccessHandler =
                new RedirectServerLogoutSuccessHandler();

        final String DEFAULT_LOGOUT_SUCCESS_URL = "/";

        URI logoutSuccessUrl = URI.create(DEFAULT_LOGOUT_SUCCESS_URL);

        redirectServerLogoutSuccessHandler.setLogoutSuccessUrl(logoutSuccessUrl);

        return redirectServerLogoutSuccessHandler;
    }


}
