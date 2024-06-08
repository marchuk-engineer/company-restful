package smida.techtask.controllers.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
public class ReportDto {

    private UUID id;

    private Instant reportDate;

    @NotNull
    private BigDecimal totalRevenue;

    @NotNull
    private BigDecimal netProfit;
}
