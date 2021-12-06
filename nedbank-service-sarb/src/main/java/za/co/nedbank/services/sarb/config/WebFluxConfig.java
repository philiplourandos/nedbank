package za.co.nedbank.services.sarb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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

    @Bean
    public Decoder feignDecoder(final ObjectMapper mapper) {
        final HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(mapper);
        final ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);

        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }
}
