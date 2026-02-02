package co.istad.polin.pipeline_service.config;

import co.istad.polin.pipeline_service.client.AccountClient;
import co.istad.polin.pipeline_service.client.JsonPlaceHolderClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class HttpInterfaceConfig {

    @Bean
    public JsonPlaceHolderClient jsonPlaceholderClient(HttpInterfaceFactory factory) {
        return factory
                .createNormalClient("https://jsonplaceholder.typicode.com",
                        JsonPlaceHolderClient.class);
    }

    @Bean
    public AccountClient accountClient(HttpInterfaceFactory factory) {
        return factory
                .createLoadBalancedClient("http://account",
                        AccountClient.class);
    }

}



