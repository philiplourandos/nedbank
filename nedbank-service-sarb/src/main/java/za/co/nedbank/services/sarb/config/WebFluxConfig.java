package za.co.nedbank.services.sarb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import za.co.nedbank.services.sarb.handler.GetRateHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration(proxyBeanMethods = false)
public class WebFluxConfig {
    @Bean
    public RouterFunction<ServerResponse> ratesRoute(final GetRateHandler requestHandler) {
        return route(GET("/sarb/rate/{type}"), requestHandler::handle);
    }
}
