package za.co.nedbank.service.sarb;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import za.co.nedbank.service.sarb.client.SarbClient;
import za.co.nedbank.service.sarb.model.Rate;

@SpringBootTest(
        properties = {"za.co.nedbank.service.sarb.url=8888"},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class RetrieveRatesTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private CacheManager manager;

    @Value("${spring.cache.cache-names}")
    private String cacheName;

    @MockBean
    private SarbClient sarbClient;

    @BeforeEach
    public void setup() {
        final Cache sarbCache = manager.getCache(cacheName);
        sarbCache.put(RateEnum.CPI.getCacheKey(), TestConst.CPI);
        sarbCache.put(RateEnum.PPI.getCacheKey(), TestConst.PPI);
        sarbCache.put(RateEnum.PRIME.getCacheKey(), TestConst.PRIME);
        sarbCache.put(RateEnum.REPO.getCacheKey(), TestConst.REPO);

        final List<Rate> noRates = Collections.emptyList();

        when(sarbClient.getRates()).thenReturn(noRates);
    }

    @Test
    public void givenLoadedRepoRateWhenRequestMadeThenReturnRepoRateAnd200() throws Exception {
        client.get()
                .uri("/sarb/rate/repo")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.rate")
                .isEqualTo(TestConst.REPO);
    }

    @Test
    public void givenLoadedPPIRateWhenRequestMadeThenReturnPPIRateAnd200() throws Exception {
        client.get()
                .uri("/sarb/rate/ppi")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.rate")
                .isEqualTo(TestConst.PPI);
    }

    @Test
    public void givenLoadedCPIRateWhenRequestMadeThenReturnCPIRateAnd200() throws Exception {
        client.get()
                .uri("/sarb/rate/cpi")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.rate")
                .isEqualTo(TestConst.CPI);
    }

    @Test
    public void givenLoadedPrimeRateWhenRequestMadeThenReturnPrimeRateAnd200() throws Exception {
        client.get()
                .uri("/sarb/rate/prime")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.rate")
                .isEqualTo(TestConst.PRIME);
    }

    @Test
    public void givenInvalidRateTypeWhenRequestMadeThenReturnWith404() throws Exception {
        client.get().uri("/sarb/rate/blah").exchange().expectStatus().isNotFound();
    }
}
