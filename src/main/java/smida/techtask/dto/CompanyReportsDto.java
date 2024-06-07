package smida.techtask.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class CompanyReportsDto {

    private UUID id;

    private String name;

    private String registrationNumber;

    private String address;

    private Instant createdAt;

    private List<ReportDto> reports;
}
