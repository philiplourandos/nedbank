package za.co.nedbank.service.handler;

import java.util.Locale;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ResourceBundleHandler {

    private static final String[] EMPTY_ARGS = {};

    private final ReloadableResourceBundleMessageSource resourceBundle;

    public ResourceBundleHandler(final ReloadableResourceBundleMessageSource bundle) {
        this.resourceBundle = bundle;
    }

    public Mono<ServerResponse> getResourceBundleEntry(final ServerRequest request) {
        final String resourcKey = request.pathVariable("key");

        try {
            final String textValue = resourceBundle.getMessage(resourcKey, EMPTY_ARGS, Locale.ENGLISH);

            return ServerResponse.ok().bodyValue(new ResourceBundleResponse(textValue));
        } catch (NoSuchMessageException noMessage) {
            return ServerResponse.notFound().build();
        }
    }
}

record ResourceBundleResponse(String text) {
}
