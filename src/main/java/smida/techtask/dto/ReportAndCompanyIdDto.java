package smida.techtask.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
public class ReportAndCompanyIdDto {

    private UUID id;

    private Instant reportDate;

    private BigDecimal totalRevenue;

    private BigDecimal netProfit;

    private UUID companyId;
}
