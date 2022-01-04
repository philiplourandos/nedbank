package za.co.nedbank.service.sarb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

public record Rate(@JsonProperty("Name") String name, @JsonProperty("SectionName") String sectionName,
        @JsonProperty("SectionId") String sectionId, @JsonProperty("TimeseriesCode") String timeSeriesCode,
        @JsonProperty("Date") LocalDate date, @JsonProperty("Value") BigDecimal rateValue) {
}
