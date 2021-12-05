package za.co.nedbank.services.sarb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@ActiveProfiles({"test"})
public class RetrieveRatesTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void givenLoadedRepoRate_whenRequestMade_thenReturnRepoRateAnd200() throws Exception {
        client.get().uri("/sarb/rate/repo")
        .expectStatus().isOk()
		.expectBody()
		.jsonPath("$.rate").isEqualTo(TestConst.REPO);
    }
    
    @Test
    public void givenLoadedPPIRate_whenRequestMade_thenReturnPPIRateAnd200() throws Exception {
        client.get().uri("/sarb/rate/repo")
        .expectStatus().isOk()
		.expectBody()
		.jsonPath("$.rate").isEqualTo(TestConst.PPI);
    }

    @Test
    public void givenLoadedCPIRate_whenRequestMade_thenReturnCPIRateAnd200() throws Exception {
        client.get().uri("/sarb/rate/repo")
        .expectStatus().isOk()
		.expectBody()
		.jsonPath("$.rate").isEqualTo(TestConst.CPI);
    }

    @Test
    public void givenLoadedPrimeRate_whenRequestMade_thenReturnPrimeRateAnd200() throws Exception {
        client.get().uri("/sarb/rate/repo")
        .expectStatus().isOk()
		.expectBody()
		.jsonPath("$.rate").isEqualTo(TestConst.PRIME);
    }
}
