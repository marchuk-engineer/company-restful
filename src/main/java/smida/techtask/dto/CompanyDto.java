package smida.techtask.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class CompanyDto {

    private UUID id;

    private String name;

    private String registrationNumber;

    private String address;

    private Instant createdAt;

}
