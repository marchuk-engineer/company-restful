package smida.techtask.mappers;

import org.mapstruct.*;
import smida.techtask.dto.CompanyDto;
import smida.techtask.entities.Company;

import java.util.List;

/**
 * Mapper interface for converting between {@link Company} entities and {@link CompanyDto} DTOs.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanyMapper {

    /**
     * Converts a {@link Company} entity to a {@link CompanyDto} DTO.
     *
     * @param company The {@link Company} entity to convert.
     * @return The corresponding {@link CompanyDto} DTO.
     */
    CompanyDto toDto(Company company);

    /**
     * Converts a {@link CompanyDto} DTO to a {@link Company} entity.
     *
     * @param dto The {@link CompanyDto} DTO to convert.
     * @return The corresponding {@link Company} entity.
     */
    Company toEntity(CompanyDto dto);

    /**
     * Converts a list of {@link CompanyDto} DTOs to a list of {@link Company} entities.
     *
     * @param companyDto The list of {@link CompanyDto} DTOs to convert.
     * @return The corresponding list of {@link Company} entities.
     */
    List<Company> toEntity(List<CompanyDto> companyDto);

    /**
     * Converts a list of {@link Company} entities to a list of {@link CompanyDto} DTOs.
     *
     * @param company The list of {@link Company} entities to convert.
     * @return The corresponding list of {@link CompanyDto} DTOs.
     */
    List<CompanyDto> toDto(List<Company> company);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void update(Company source, @MappingTarget Company target);

}

