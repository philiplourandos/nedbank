package za.co.nedbank.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import za.co.nedbank.service.handler.ResourceBundleHandler;

@Configuration
public class ResourceBundleConfig {
    
    @Bean
    public RouterFunction<ServerResponse> routes(final ResourceBundleHandler handler) {
        route(GET("/nedbank/bpm/resourcebundle/{key}"))
    }
}
