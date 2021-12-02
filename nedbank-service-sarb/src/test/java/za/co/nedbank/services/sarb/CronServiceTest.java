package za.co.nedbank.services.sarb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@ActiveProfiles({"test"})
public class CronServiceTest {
    @Test
    public void givenConfiguredCron_whenTrigger_thenRetrieveSarbValuesAndCache() throws Exception {
        fail();
    }
}
