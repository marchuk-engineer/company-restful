package smida.techtask.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import smida.techtask.controllers.dto.CompanyReportsDto;
import smida.techtask.controllers.dto.ReportDto;
import smida.techtask.entities.Company;
import smida.techtask.entities.Report;

import java.util.List;

/**
 * Mapper interface for converting between {@link Company} entities and {@link CompanyReportsDto} DTOs,
 * as well as between {@link Report} entities and {@link ReportDto} DTOs.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanyReportsMapper {

    /**
     * Converts a {@link Company} entity to a {@link CompanyReportsDto} DTO.
     *
     * @param company The {@link Company} entity to convert.
     * @return The corresponding {@link CompanyReportsDto} DTO.
     */
    CompanyReportsDto toDto(Company company);

    /**
     * Converts a {@link Report} entity to a {@link ReportDto} DTO.
     *
     * @param report The {@link Report} entity to convert.
     * @return The corresponding {@link ReportDto} DTO.
     */
    ReportDto toDto(Report report);

    /**
     * Converts a {@link CompanyReportsDto} DTO to a {@link Company} entity.
     *
     * @param companyReportsDto The {@link CompanyReportsDto} DTO to convert.
     * @return The corresponding {@link Company} entity.
     */
    Company toEntity(CompanyReportsDto companyReportsDto);

    /**
     * Converts a list of {@link CompanyReportsDto} DTOs to a list of {@link Company} entities.
     *
     * @param companyReportsDto The list of {@link CompanyReportsDto} DTOs to convert.
     * @return The corresponding list of {@link Company} entities.
     */
    List<Company> toEntity(List<CompanyReportsDto> companyReportsDto);

    /**
     * Converts a list of {@link Company} entities to a list of {@link CompanyReportsDto} DTOs.
     *
     * @param company The list of {@link Company} entities to convert.
     * @return The corresponding list of {@link CompanyReportsDto} DTOs.
     */
    List<CompanyReportsDto> toDto(List<Company> company);

}
