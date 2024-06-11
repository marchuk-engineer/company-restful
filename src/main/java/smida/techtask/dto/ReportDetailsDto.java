package smida.techtask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link smida.techtask.entities.ReportDetails}
 */
@Data
public class ReportDetailsDto implements Serializable {

    @Schema(description = "Unique identifier of the report", example = "123e4567-e89b-12d3-a456-426614174001")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID reportId;

    @Schema(description = "Detailed financial data of the report", example = "Revenue breakdown, expenses, etc.")
    private String financialData;

    @Schema(description = "Comments or notes on the report", example = "This quarter's performance was above expectations.")
    private String comments;
}