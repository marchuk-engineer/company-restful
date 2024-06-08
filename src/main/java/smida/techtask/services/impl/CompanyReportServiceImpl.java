package smida.techtask.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smida.techtask.dto.CompanyReportsDto;
import smida.techtask.dto.ReportDto;
import smida.techtask.repositories.managers.CompanyManager;
import smida.techtask.repositories.managers.CompanyReportsManager;
import smida.techtask.mappers.CompanyReportsMapper;
import smida.techtask.mappers.ReportMapper;
import smida.techtask.services.CompanyReportService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyReportServiceImpl implements CompanyReportService {

    private final CompanyReportsManager companyReportsManager;
    private final CompanyManager companyManager;
    private final ReportMapper reportMapper;
    private final CompanyReportsMapper companyReportsMapper;

    @Override
    public CompanyReportsDto getAllByCompanyId(UUID companyId) {
        return companyReportsMapper.toDto(companyManager.getById(companyId));
    }

    @Override
    public ReportDto getById(UUID companyId, UUID reportId) {
        return reportMapper.toDto(companyReportsManager.getById(companyId, reportId));
    }

    @Override
    public ReportDto createReportById(UUID companyId, ReportDto reportDto) {
        return reportMapper.toDto(companyReportsManager.save(companyId, reportMapper.toEntity(reportDto)));
    }

    @Override
    public ReportDto updateReport(UUID companyId, UUID reportId, ReportDto reportDto) {
        return reportMapper.toDto(companyReportsManager.update(companyId, reportId, reportMapper.toEntity(reportDto)));
    }

    @Override
    public void deleteReport(UUID companyId, UUID reportId) {
        companyReportsManager.delete(companyId, reportId);
    }

}
