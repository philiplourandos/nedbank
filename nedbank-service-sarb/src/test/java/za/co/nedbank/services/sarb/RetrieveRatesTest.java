package za.co.nedbank.services.sarb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@ActiveProfiles({"test"})
public class RetrieveRatesTest {

    @Test
    public void givenLoadedRepoRate_whenRequestMade_thenReturnRepoRateAnd200() throws Exception {
        fail();
    }
    
    @Test
    public void givenLoadedPPIRate_whenRequestMade_thenReturnPPIRateAnd200() throws Exception {
        fail();
    }

    @Test
    public void givenLoadedCPIRate_whenRequestMade_thenReturnCPIRateAnd200() throws Exception {
        fail();
    }

    @Test
    public void givenLoadedPrimeRate_whenRequestMade_thenReturnPrimeRateAnd200() throws Exception {
        fail();
    }
}
