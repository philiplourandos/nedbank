package za.co.nedbank.service.sarb;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import za.co.nedbank.service.sarb.service.SarbService;

@Component
public class ApplicationEventListener {
    private final SarbService sarbService;

    public ApplicationEventListener(final SarbService service) {
        this.sarbService = service;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        sarbService.retrieveRates();
    }
}
