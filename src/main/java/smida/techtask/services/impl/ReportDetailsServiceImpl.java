package smida.techtask.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smida.techtask.dto.ReportDetailsDto;
import smida.techtask.entities.Report;
import smida.techtask.mappers.ReportDetailsMapper;
import smida.techtask.repositories.managers.CompanyManager;
import smida.techtask.repositories.managers.ReportDetailsManager;
import smida.techtask.repositories.managers.ReportManager;
import smida.techtask.services.ReportDetailsService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportDetailsServiceImpl implements ReportDetailsService {

    private final ReportDetailsManager reportDetailsManager;
    private final ReportManager reportManager;
    private final CompanyManager companyManager;
    private final ReportDetailsMapper reportDetailsMapper;


    @Override
    public List<ReportDetailsDto> getAll() {
        return reportDetailsMapper.toDto(reportDetailsManager.findAll());
    }

    @Override
    public ReportDetailsDto getByIds(UUID companyId, UUID reportId, UUID id) {
        companyManager.getById(companyId);
        reportManager.findById(reportId);
        return reportDetailsMapper.toDto(reportDetailsManager.findById(id));
    }

    @Override
    public ReportDetailsDto getByReportId(UUID reportId) {
        Report report = reportManager.findById(reportId);
        return reportDetailsMapper.toDto(reportDetailsManager.findById(report.getId()));
    }

    @Override
    public ReportDetailsDto create(UUID companyId, UUID reportId, ReportDetailsDto reportDetailsDto) {
        companyManager.getById(companyId);
        reportManager.findById(reportId);
        return reportDetailsMapper.toDto(reportDetailsManager.save(reportDetailsMapper.toEntity(reportDetailsDto)));
    }

    @Override
    @Transactional
    public ReportDetailsDto update(UUID companyId, UUID reportId, UUID uuid, ReportDetailsDto dto) {
        companyManager.getById(companyId);
        reportManager.findById(reportId);
        return reportDetailsMapper.toDto(reportDetailsManager.update(uuid, reportDetailsMapper.toEntity(dto)));
    }

    @Override
    public void deleteById(UUID companyId, UUID reportId, UUID uuid) {
        companyManager.getById(companyId);
        reportManager.findById(reportId);
        reportDetailsManager.delete(reportDetailsManager.findById(uuid));
    }

}
