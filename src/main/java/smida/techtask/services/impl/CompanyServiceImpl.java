package smida.techtask.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smida.techtask.controllers.dto.CompanyDto;
import smida.techtask.entities.Company;
import smida.techtask.managers.CompanyManager;
import smida.techtask.mappers.CompanyMapper;
import smida.techtask.services.CompanyService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {


    private final CompanyManager companyManager;
    private final CompanyMapper companyMapper;

    @Override
    public List<CompanyDto> getAll() {
        return companyManager.getAll().stream()
                .map(companyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDto getById(UUID id) {
        return companyMapper.toDto(companyManager.getById(id));
    }

    @Override
    public CompanyDto save(CompanyDto companyDto) {
        Company company = companyMapper.toEntity(companyDto);
        return companyMapper.toDto(companyManager.save(company));
    }

    @Override
    public CompanyDto update(UUID id, CompanyDto companyDto) {
        return companyMapper.toDto(companyManager.update(id, companyMapper.toEntity(companyDto)));
    }

    @Override
    public void deleteById(UUID id) {
        companyManager.deleteById(id);
    }

}
