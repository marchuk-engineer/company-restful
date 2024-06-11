package smida.techtask.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smida.techtask.dto.CompanyReportsDto;
import smida.techtask.dto.ReportDto;
import smida.techtask.entities.Company;
import smida.techtask.entities.Report;
import smida.techtask.exceptions.notfound.ReportNotFoundException;
import smida.techtask.mappers.CompanyReportsMapper;
import smida.techtask.mappers.ReportMapper;
import smida.techtask.repositories.managers.CompanyManager;
import smida.techtask.repositories.managers.CompanyReportsManager;
import smida.techtask.services.CompanyReportService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyReportsServiceImpl implements CompanyReportService {

    private final CompanyReportsManager companyReportsManager;
    private final CompanyManager companyManager;
    private final ReportMapper reportMapper;
    private final CompanyReportsMapper companyReportsMapper;
    private final ReportSagaService reportSagaService;

    @Override
    public CompanyReportsDto getAllByCompanyId(UUID companyId) {
        return companyReportsMapper.toDto(companyManager.getById(companyId));
    }

    @Override
    public ReportDto getById(UUID companyId, UUID reportId) {
        Company existingCompany = companyManager.getById(companyId);
        Report existingReport = companyReportsManager.findById(reportId);
        if (existingCompany.getReports().contains(existingReport)) {
            return reportMapper.toDto(existingReport);
        }
        throw new ReportNotFoundException(reportId);
    }

    @Override
    public ReportDto createReportById(UUID companyId, ReportDto reportDto) {
        Company existingCompany = companyManager.getById(companyId);
        Report report = reportMapper.toEntity(reportDto);
        report.setCompany(existingCompany);
        return reportMapper.toDto(companyReportsManager.save(report));
    }

    @Override
    public ReportDto updateReport(UUID companyId, UUID reportId, ReportDto reportDto) {
        Company existingCompany = companyManager.getById(companyId);
        Report existingReport = companyReportsManager.findById(reportId);
        if (existingCompany.getReports().contains(existingReport)) {
            reportMapper.update(reportMapper.toEntity(reportDto), existingReport);
            companyReportsManager.save(existingReport);
            reportMapper.toDto(existingReport);
        }
        throw new ReportNotFoundException(reportId);
    }

    @Override
    public void deleteReport(UUID companyId, UUID reportId) {
        Company existingCompany = companyManager.getById(companyId);
        Report existingReport = companyReportsManager.findById(reportId);
        if (existingCompany.getReports().contains(existingReport)) {
            reportSagaService.deleteReportAndDetails(existingReport);
        }
        throw new ReportNotFoundException(reportId);
    }

}
