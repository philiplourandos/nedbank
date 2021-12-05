package za.co.nedbank.services.sarb;

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"test"})
public class NoRatesTest {
    @Test
    public void givenNoLoadedRepoRate_whenRequestMade_thenReturn204WithNoBody() throws Exception {
        fail();
    }
    
    @Test
    public void givenNoLoadedPPIRate_whenRequestMade_thenReturn204WithNoBody() throws Exception {
        fail();
    }

    @Test
    public void givenNoLoadedCPIRate_whenRequestMade_thenReturn204WithNoBody() throws Exception {
        fail();
    }

    @Test
    public void givenNoLoadedPrimeRate_whenRequestMade_thenReturn204WithNoBody() throws Exception {
        fail();
    }
}
