package za.co.nedbank.service.sarb;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

@SpringBootTest(
        properties = {"za.co.nedbank.service.sarb.url=http://localhost:${wiremock.server.port}"},
        webEnvironment = WebEnvironment.NONE)
@ActiveProfiles({"test"})
@AutoConfigureWireMock(
        files = {"classpath:/stubs"},
        port = 0)
public class CronServiceTest {
    private static final int WAIT_TIME = 40;

    @Autowired
    private CacheManager manager;

    @Value("${spring.cache.cache-names}")
    private String cacheName;

    @Test
    public void givenConfiguredCronWhenTriggerThenRetrieveSarbValuesAndCache() throws Exception {
        final Cache sarbCache = manager.getCache(cacheName);

        await().atMost(WAIT_TIME, TimeUnit.SECONDS).untilAsserted(() -> {
            assertEquals(TestConst.CPI, sarbCache.get(RateEnum.CPI.getCacheKey(), BigDecimal.class));
            assertEquals(TestConst.PPI, sarbCache.get(RateEnum.PPI.getCacheKey(), BigDecimal.class));
            assertEquals(TestConst.REPO, sarbCache.get(RateEnum.REPO.getCacheKey(), BigDecimal.class));
            assertEquals(TestConst.PRIME, sarbCache.get(RateEnum.PRIME.getCacheKey(), BigDecimal.class));
        });
    }
}
