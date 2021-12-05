package za.co.nedbank.services.sarb;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {"za.co.nedbank.service.sarb.url=http://localhost:${wiremock.server.port}"},
        webEnvironment = WebEnvironment.NONE)
@ActiveProfiles({"test"})
@AutoConfigureWireMock(files = {"classpath:/stubs"}, port = 0)
public class CronServiceTest {
    @Autowired
    private CacheManager manager;
    
    @Value("${spring.cache.cache-names}")
    private String cacheName;
    
    @Test
    public void givenConfiguredCron_whenTrigger_thenRetrieveSarbValuesAndCache() throws Exception {
        //given
        final BigDecimal cpi = new BigDecimal("5.0000");
        final BigDecimal ppi = new BigDecimal("8.1162");
        final BigDecimal repo = new BigDecimal("3.7500");
        final BigDecimal prime = new BigDecimal("7.2500");

        final Cache sarbCache = manager.getCache(cacheName);

        await().atMost(40, TimeUnit.SECONDS).untilAsserted(() -> {
            assertEquals(cpi, sarbCache.get(RateEnum.CPI.getCacheKey(), BigDecimal.class));
            assertEquals(ppi, sarbCache.get(RateEnum.PPI.getCacheKey(), BigDecimal.class));
            assertEquals(repo, sarbCache.get(RateEnum.REPO.getCacheKey(), BigDecimal.class));
            assertEquals(prime, sarbCache.get(RateEnum.PRIME.getCacheKey(), BigDecimal.class));
        });
    }
}
