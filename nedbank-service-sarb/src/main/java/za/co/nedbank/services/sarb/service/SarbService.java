package za.co.nedbank.services.sarb.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import za.co.nedbank.services.sarb.RateEnum;
import za.co.nedbank.services.sarb.client.SarbClient;
import za.co.nedbank.services.sarb.model.Rate;

@Service
public class SarbService {
    private static final Logger LOG = LoggerFactory.getLogger(SarbService.class);

    private final SarbClient client;
    private final CacheManager manager;

    @Value("${spring.cache.cache-names}")
    private String cacheName;

    public SarbService(SarbClient client, CacheManager manager) {
        this.client = client;
        this.manager = manager;
    }

    @Scheduled(cron = "${za.co.nedbank.service.sarb.cron}")
    public void retrieveRates() {
        LOG.info("Retrieving SARB rates");

        final List<Rate> rates = client.getRates();

        final Optional<Rate> cpi = rates.stream().filter(p -> RateEnum.CPI.getJsonKey().equals(p.name())).findFirst();

        final Optional<Rate> ppi = rates.stream().filter(p -> RateEnum.PPI.getJsonKey().equals(p.name())).findFirst();

        final Optional<Rate> repo = rates.stream().filter(p -> RateEnum.REPO.getJsonKey().equals(p.name())).findFirst();

        final Optional<Rate> prime = rates.stream().filter(p -> RateEnum.PRIME.getJsonKey().equals(p.name()))
                .findFirst();

        final Cache sarbCache = manager.getCache(cacheName);
        sarbCache.put(RateEnum.CPI, cpi.get().rateValue());
        sarbCache.put(RateEnum.PPI, ppi.get().rateValue());
        sarbCache.put(RateEnum.PRIME, prime.get().rateValue());
        sarbCache.put(RateEnum.REPO, repo.get().rateValue());

        LOG.info("Populated cache with SARB rates");
    }
}
