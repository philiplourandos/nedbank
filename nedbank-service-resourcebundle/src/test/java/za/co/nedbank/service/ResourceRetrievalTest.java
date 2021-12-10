package za.co.nedbank.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@AutoConfigureWireMock(files = {"classpath:/stubs"}, port = 8888)
public class ResourceRetrievalTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void givenExistingKeyInResourceBundle_whenRequested_thenReturnValueWith200() throws Exception {
        final String expectedText = "ken sent me";

        webClient.get()
                .uri("/nedbank/bpm/resourcebundle/rrb.test")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.text").isEqualTo(expectedText);
    }

    @Test
    public void givenKeyNotInBundle_whenSubmitted_thenReturn404() throws Exception {
        webClient.get()
                .uri("/nedbank/bpm/resourcebundle/nb.cardano.statement")
                .exchange()
                .expectStatus().isNotFound();
    }
}
