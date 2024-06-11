package smida.techtask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
public class ReportAndCompanyIdDto {

    @Schema(description = "Unique identifier of the report", example = "123e4567-e89b-12d3-a456-426614174001")
    private UUID id;

    @Schema(description = "Date of the report", example = "2023-06-10T15:30:00Z")
    private Instant reportDate;

    @Schema(description = "Total revenue reported", example = "1000000.00")
    private BigDecimal totalRevenue;

    @Schema(description = "Net profit reported", example = "250000.00")
    private BigDecimal netProfit;

    @Schema(description = "Unique identifier of the company", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID companyId;
}
