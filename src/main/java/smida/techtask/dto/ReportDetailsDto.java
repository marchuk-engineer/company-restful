package smida.techtask.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link smida.techtask.entities.ReportDetails}
 */
@Data
public class ReportDetailsDto implements Serializable {
    private UUID reportId;
    private String financialData;
    private String comments;
}