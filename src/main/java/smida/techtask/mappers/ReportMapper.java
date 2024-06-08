package smida.techtask.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import smida.techtask.controllers.dto.ReportDto;
import smida.techtask.entities.Report;

import java.util.List;

/**
 * Mapper interface for converting between {@link ReportDto} DTOs and {@link Report} entities.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportMapper {

    /**
     * Converts a {@link ReportDto} DTO to a {@link Report} entity.
     *
     * @param reportDto The {@link ReportDto} DTO to convert.
     * @return The corresponding {@link Report} entity.
     */
    Report toEntity(ReportDto reportDto);

    /**
     * Converts a list of {@link ReportDto} DTOs to a list of {@link Report} entities.
     *
     * @param reportDto The list of {@link ReportDto} DTOs to convert.
     * @return The corresponding list of {@link Report} entities.
     */
    List<Report> toEntity(List<ReportDto> reportDto);

    /**
     * Converts a {@link Report} entity to a {@link ReportDto} DTO.
     *
     * @param report The {@link Report} entity to convert.
     * @return The corresponding {@link ReportDto} DTO.
     */
    ReportDto toDto(Report report);

}
