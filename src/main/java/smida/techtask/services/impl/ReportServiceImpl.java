package smida.techtask.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smida.techtask.controllers.dto.ReportAndCompanyIdDto;
import smida.techtask.repositories.managers.ReportManager;
import smida.techtask.mappers.ReportAndCompanyIdMapper;
import smida.techtask.services.ReportService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportManager reportManager;
    private final ReportAndCompanyIdMapper reportAndCompanyIdMapper;

    @Override
    public List<ReportAndCompanyIdDto> getAll() {
        return reportAndCompanyIdMapper.toDto(reportManager.findAll());
    }

}
