package za.co.nedbank.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Db2Container;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class ArchivingTest {
    
    @Container
    private static final Db2Container DB2 = new Db2Container("ibmcom/db2:11.5.7.0a");
    
    @Test
    public void givenValidData_whenSelected_thenSuccessfullyArchive() throws Exception {
        
    }

    @Test
    public void givenBpmGeneratesAFault_whenJobRuns_thenLogFailure() throws Exception {
        
    }
    
    @DynamicPropertySource
    static final void register(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", DB2::getJdbcUrl);
        registry.add("spring.datasource.username", DB2::getUsername);
        registry.add("spring.datasource.password", DB2::getPassword);
    }
}
