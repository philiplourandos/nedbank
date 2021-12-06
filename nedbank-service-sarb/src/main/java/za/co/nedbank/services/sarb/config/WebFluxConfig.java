package za.co.nedbank.services.sarb.config;

import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import za.co.nedbank.services.sarb.RateEnum;
import za.co.nedbank.services.sarb.model.RateResponse;
import za.co.nedbank.services.sarb.service.SarbService;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration(proxyBeanMethods = false)
public class WebFluxConfig {
    @Bean
    public RouterFunction<ServerResponse> ratesRoute(final SarbService service) {
        return route(GET("/sarb/rate/{type}"), request -> {
            final String requiredRate = request.pathVariable("type");

            try {
                final RateEnum required = RateEnum.valueOf(requiredRate.toUpperCase());
                final Optional<BigDecimal> foundRate = service.getRate(required);

                if (foundRate.isPresent()) {
                    return ServerResponse.ok().bodyValue(new RateResponse(foundRate.get()));
                } else {
                    return ServerResponse.noContent().build();
                }
            } catch (Exception e) {
                return ServerResponse.notFound().build();
            }
        });
    }
}
