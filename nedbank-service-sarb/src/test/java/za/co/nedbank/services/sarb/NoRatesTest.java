package za.co.nedbank.services.sarb;

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(properties = {"za.co.nedbank.service.sarb.url=8888"},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
public class NoRatesTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void givenNoLoadedRepoRate_whenRequestMade_thenReturn204WithNoBody() throws Exception {
        client.get().uri("/sarb/rate/repo")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }
    
    @Test
    public void givenNoLoadedPPIRate_whenRequestMade_thenReturn204WithNoBody() throws Exception {
        client.get().uri("/sarb/rate/ppi")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void givenNoLoadedCPIRate_whenRequestMade_thenReturn204WithNoBody() throws Exception {
        client.get().uri("/sarb/rate/cpi")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void givenNoLoadedPrimeRate_whenRequestMade_thenReturn204WithNoBody() throws Exception {
        client.get().uri("/sarb/rate/prime")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }
}
