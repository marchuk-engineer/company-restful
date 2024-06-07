package smida.techtask.mappers;

import org.mapstruct.*;
import smida.techtask.controllers.dto.CompanyDto;
import smida.techtask.entities.Company;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanyMapper {
    CompanyDto toDto(Company company);

    Company toEntity(CompanyDto dto);
    
}
