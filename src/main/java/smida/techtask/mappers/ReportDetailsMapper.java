package smida.techtask.mappers;

import org.mapstruct.*;
import smida.techtask.dto.ReportDetailsDto;
import smida.techtask.entities.ReportDetails;

import java.util.List;

/**
 * Mapper interface for converting between {@link ReportDetails} entities and {@link ReportDetailsDto} DTOs.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportDetailsMapper {

    /**
     * Converts a {@link ReportDetailsDto} DTO to a {@link ReportDetails} entity.
     *
     * @param dto The DTO to convert.
     * @return The corresponding entity.
     */
    ReportDetails toEntity(ReportDetailsDto dto);

    /**
     * Converts a {@link ReportDetails} entity to a {@link ReportDetailsDto} DTO.
     *
     * @param entity The entity to convert.
     * @return The corresponding DTO.
     */
    ReportDetailsDto toDto(ReportDetails entity);

    /**
     * Converts a list of {@link ReportDetailsDto} DTOs to a list of {@link ReportDetails} entities.
     *
     * @param dto The list of DTOs to convert.
     * @return The corresponding list of entities.
     */
    List<ReportDetails> toEntity(List<ReportDetailsDto> dto);

    /**
     * Converts a list of {@link ReportDetails} entities to a list of {@link ReportDetailsDto} DTOs.
     *
     * @param entity The list of entities to convert.
     * @return The corresponding list of DTOs.
     */
    List<ReportDetailsDto> toDto(List<ReportDetails> entity);

    /**
     * Updates the properties of a {@link ReportDetails} entity from another entity.
     *
     * @param source The source entity containing updated properties.
     * @param target The target entity to update.
     */
    @Mapping(target = "reportId", ignore = true)
    void update(ReportDetails source, @MappingTarget ReportDetails target);

}
