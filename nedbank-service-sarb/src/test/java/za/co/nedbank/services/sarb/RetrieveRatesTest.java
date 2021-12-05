package za.co.nedbank.services.sarb;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import za.co.nedbank.services.sarb.service.SarbService;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(properties = {"za.co.nedbank.service.sarb.url=http://localhost:${wiremock.server.port}"})
@ActiveProfiles({"test"})
public class RetrieveRatesTest {

    @MockBean
    private SarbService service;
    
    @Autowired
    private WebTestClient client;

    @Test
    public void givenLoadedRepoRate_whenRequestMade_thenReturnRepoRateAnd200() throws Exception {
        //given
        when(service.getRate(eq(RateEnum.REPO))).thenReturn(Optional.of(TestConst.REPO));

        //when
        client.get().uri("/sarb/rate/repo")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.rate").isEqualTo(TestConst.REPO);

        //then
        verify(service, times(1)).getRate(eq(RateEnum.REPO));
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
