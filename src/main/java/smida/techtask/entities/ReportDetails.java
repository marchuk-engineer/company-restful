package smida.techtask.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ReportDetails {

    @Id
    private UUID reportId;

    private String financialData;

    private String comments;

}
