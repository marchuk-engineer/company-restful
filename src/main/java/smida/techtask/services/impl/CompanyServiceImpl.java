package smida.techtask.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import smida.techtask.dto.CompanyDto;
import smida.techtask.entities.Company;
import smida.techtask.mappers.CompanyMapper;
import smida.techtask.repositories.managers.CompanyManager;
import smida.techtask.services.CompanyService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class CompanyServiceImpl implements CompanyService {

    private final CompanyManager companyManager;
    private final CompanySagaService saga;
    private final CompanyMapper companyMapper;

    @Override
    public List<CompanyDto> getAll() {
        return companyMapper.toDto(companyManager.getAll());
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
        saga.deleteCompanyAndAssociatedData(companyManager.getById(id));
    }

}
