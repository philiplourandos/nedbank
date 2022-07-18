package za.co.nedbank.service;

import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import za.co.nedbank.service.dao.CaseInfo;
import za.co.nedbank.service.dao.CaseInfoMapper;

@SpringBootApplication
@EnableBatchProcessing
@Configuration
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<CaseInfo> caseReader(final DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<>()
                .dataSource(dataSource)
                .fetchSize(100)
                .sql("""
                     SELECT * FROM ARCH_CTL WHERE ARCHIVE_DT IS NULL ORDER BY REQUEST_DT
                     """)
                .rowMapper(new CaseInfoMapper())
                .build();
    }
}
