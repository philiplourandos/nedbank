package za.co.nedbank.services.sarb;

import reactor.core.publisher.Mono;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import za.co.nedbank.services.sarb.service.SarbService;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableCaching
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //Tells Spring to do this once application is ready to start running
    @EventListener(ApplicationReadyEvent.class)
    public final void onStart(final SarbService service) {
        service.retrieveRates();
    }
    //This value will be managed by Spring container
    @Bean
    public final RouterFunction ratesRoute(final SarbService service) {

        return RouterFunctions.route().GET("/sarb/rate/{rate}", request -> {
            final String requiredRate = request.pathVariable("rate");
            try {
                final RateEnum required = RateEnum.valueOf(requiredRate.toUpperCase());
                final Optional<BigDecimal> foundRate = service.getRate(required);
                return Mono.just(foundRate.ifPresentOrElse(a -> ServerResponse.ok().bodyValue(a),
                        () -> ServerResponse.noContent().build()));
            } catch (Exception e) {
                return ServerResponse.notFound().build();
            }
        }).build();

    }
}
