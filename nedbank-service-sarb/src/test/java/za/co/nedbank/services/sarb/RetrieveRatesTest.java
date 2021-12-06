package za.co.nedbank.services.sarb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(properties = {"za.co.nedbank.service.sarb.url=http://localhost:${wiremock.server.port}"})
@ActiveProfiles({"test"})
public class RetrieveRatesTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private CacheManager manager;
    
    @Value("${spring.cache.cache-names}")
    private String cacheName;
    
    @BeforeEach
    public void setup() {
        final Cache sarbCache = manager.getCache(cacheName);
        sarbCache.put(RateEnum.CPI.getCacheKey(), TestConst.CPI);
        sarbCache.put(RateEnum.PPI.getCacheKey(), TestConst.PPI);
        sarbCache.put(RateEnum.PRIME.getCacheKey(), TestConst.PRIME);
        sarbCache.put(RateEnum.REPO.getCacheKey(), TestConst.REPO);
    }

    @Test
    public void givenLoadedRepoRate_whenRequestMade_thenReturnRepoRateAnd200() throws Exception {
        client.get().uri("/sarb/rate/repo")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.rate").isEqualTo(TestConst.REPO);
    }

    @Test
    public void givenLoadedPPIRate_whenRequestMade_thenReturnPPIRateAnd200() throws Exception {
        client.get().uri("/sarb/rate/ppi")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.rate").isEqualTo(TestConst.PPI);
    }

    @Test
    public void givenLoadedCPIRate_whenRequestMade_thenReturnCPIRateAnd200() throws Exception {
        client.get().uri("/sarb/rate/cpi")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.rate").isEqualTo(TestConst.CPI);
    }

    @Test
    public void givenLoadedPrimeRate_whenRequestMade_thenReturnPrimeRateAnd200() throws Exception {
        client.get().uri("/sarb/rate/prime")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.rate").isEqualTo(TestConst.PRIME);
    }

    @Test
    public void givenInvalidRateType_whenRequestMade_thenReturnWith404() throws Exception {
        client.get().uri("/sarb/rate/blah")
                .exchange()
                .expectStatus().isNotFound();
    }
}
