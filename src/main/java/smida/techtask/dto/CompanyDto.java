package smida.techtask.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object of company")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyDto {

    @Schema(description = "Unique identifier of the company", example = "123e4567-e89b-12d3-a456-426614174000")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Schema(description = "Name of the company", example = "Tech Corp")
    @NotNull(message = "Name must not be null")
    private String name;

    @Schema(description = "Registration number of the company", example = "123456789")
    @NotNull(message = "Registration number must not be null")
    private String registrationNumber;

    @Schema(description = "Address of the company", example = "1234 Tech Lane, Silicon Valley, CA")
    @NotNull(message = "Address must not be null")
    private String address;

    @Schema(description = "Timestamp when the company was created", example = "2023-01-01T12:00:00Z")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdAt;
}
