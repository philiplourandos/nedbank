package za.co.nedbank.services.sarb.handler;

import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import za.co.nedbank.services.sarb.RateEnum;
import za.co.nedbank.services.sarb.model.RateResponse;
import za.co.nedbank.services.sarb.service.SarbService;

@Component
public class GetRateHandler {

    private final SarbService sarbService;

    public GetRateHandler(final SarbService service) {
        this.sarbService = service;
    }

    public Mono<ServerResponse> handle(final ServerRequest request) {
        final String requiredRate = request.pathVariable("type");

        try {
            final RateEnum required = RateEnum.valueOf(requiredRate.toUpperCase());
            final Optional<BigDecimal> foundRate = sarbService.getRate(required);

            if (foundRate.isPresent()) {
                return ServerResponse.ok().bodyValue(new RateResponse(foundRate.get()));
            } else {
                return ServerResponse.noContent().build();
            }
        } catch (Exception e) {
            return ServerResponse.notFound().build();
        }
    }
}
