package za.co.nedbank.service.sarb;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import za.co.nedbank.service.sarb.client.SarbClient;
import za.co.nedbank.service.sarb.model.Rate;

import static org.mockito.Mockito.when;

@SpringBootTest(properties = {"za.co.nedbank.service.sarb.url=8888"},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
public class NoRatesTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private SarbClient sarbClient;

    @BeforeEach
    public void setup() {
        final List<Rate> noRates = Collections.emptyList();

        when(sarbClient.getRates()).thenReturn(noRates);
    }

    @Test
    public void givenNoLoadedRepoRateWhenRequestMadeThenReturn204WithNoBody() throws Exception {
        client.get().uri("/sarb/rate/repo")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void givenNoLoadedPPIRateWhenRequestMadeThenReturn204WithNoBody() throws Exception {
        client.get().uri("/sarb/rate/ppi")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void givenNoLoadedCPIRateWhenRequestMadeThenReturn204WithNoBody() throws Exception {
        client.get().uri("/sarb/rate/cpi")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void givenNoLoadedPrimeRateWhenRequestMadeThenReturn204WithNoBody() throws Exception {
        client.get().uri("/sarb/rate/prime")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }
}
