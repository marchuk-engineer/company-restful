package smida.techtask.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object of company")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportDto {

    @Schema(description = "Unique identifier of the report", example = "123e4567-e89b-12d3-a456-426614174001")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Schema(description = "Date of the report", example = "2023-06-10T15:30:00Z")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant reportDate;

    @Schema(description = "Total revenue reported", example = "1000000.00")
    @NotNull
    private BigDecimal totalRevenue;

    @Schema(description = "Net profit reported", example = "250000.00")
    @NotNull
    private BigDecimal netProfit;
}
