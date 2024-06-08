package smida.techtask.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import smida.techtask.dto.ReportAndCompanyIdDto;
import smida.techtask.entities.Report;

import java.util.List;

/**
 * Mapper interface for converting between {@link Report} entities and {@link ReportAndCompanyIdDto} DTOs.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportAndCompanyIdMapper {

    /**
     * Converts a {@link Report} entity to a {@link ReportAndCompanyIdDto} DTO.
     *
     * @param report The {@link Report} entity to convert.
     * @return The corresponding {@link ReportAndCompanyIdDto} DTO.
     */
    @Mapping(target = "companyId", source = "company.id")
    ReportAndCompanyIdDto toDto(Report report);

    /**
     * Converts a list of {@link Report} entities to a list of {@link ReportAndCompanyIdDto} DTOs.
     *
     * @param reports The list of {@link Report} entities to convert.
     * @return The corresponding list of {@link ReportAndCompanyIdDto} DTOs.
     */
    List<ReportAndCompanyIdDto> toDto(List<Report> reports);

}
