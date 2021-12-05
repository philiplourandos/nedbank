package za.co.nedbank.services.sarb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import za.co.nedbank.services.sarb.service.SarbService;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableCaching
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //Tells Spring to do this once application is ready to start running
    @EventListener(ApplicationReadyEvent.class)
    public void onStart(final SarbService service){
        service.retrieveRates();

    }
}
