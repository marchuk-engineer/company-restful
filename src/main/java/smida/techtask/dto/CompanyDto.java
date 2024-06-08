package smida.techtask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Schema(description = "Data transfer object of company")
public class CompanyDto {

    @Schema(description = "Unique identifier of the company", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Name of the company", example = "Tech Corp")
    private String name;

    @Schema(description = "Registration number of the company", example = "123456789")
    private String registrationNumber;

    @Schema(description = "Address of the company", example = "1234 Tech Lane, Silicon Valley, CA")
    private String address;

    @Schema(description = "Timestamp when the company was created", example = "2023-01-01T12:00:00Z")
    private Instant createdAt;
}
