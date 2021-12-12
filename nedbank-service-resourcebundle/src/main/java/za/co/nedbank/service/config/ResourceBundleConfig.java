package za.co.nedbank.service.config;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import za.co.nedbank.service.handler.ResourceBundleHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ResourceBundleConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(final ResourceBundleHandler handler) {
        return route(GET("/nedbank/bpm/resourcebundle/{key}"), handler::getResourceBundleEntry);
    }

    @Bean
    public ReloadableResourceBundleMessageSource resourceBundle(
            @Value("${spring.cloud.config.uri}") final String uri,
            @Value("${spring.application.name}") final String applicationName) {
        final String resourceBundleUrl = String.format("%s/%s", uri, applicationName);

        final ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(resourceBundleUrl);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());

        return messageSource;
    }
}
