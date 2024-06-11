package smida.techtask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class CompanyReportsDto {

    @Schema(description = "Unique identifier of the company", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Name of the company", example = "ABC Corp")
    private String name;

    @Schema(description = "Company's registration number", example = "REG123456")
    private String registrationNumber;

    @Schema(description = "Address of the company", example = "123 Main St, Springfield")
    private String address;

    @Schema(description = "Timestamp when the company was created", example = "2023-06-10T15:30:00Z")
    private Instant createdAt;

    @Schema(description = "List of reports associated with the company")
    private List<ReportDto> reports;
}