package za.co.nedbank.services.sarb.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import za.co.nedbank.services.sarb.model.Rate;

@FeignClient(url = "${za.co.nedbank.service.sarb.url}")
public interface SarbClient {

    @GetMapping(path = { "/SarbWebApi/WebIndicators/HomePageRates" }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Rate> getRates();
}
