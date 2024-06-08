package smida.techtask.repositories.managers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smida.techtask.annotations.ReportNotFoundException;
import smida.techtask.entities.Company;
import smida.techtask.entities.Report;
import smida.techtask.repositories.ReportRepository;

import java.util.UUID;

/**
 * Manager class responsible for managing company reports.
 */
@Component
@RequiredArgsConstructor
public class CompanyReportsManager {

    private final CompanyManager companyManager;
    private final ReportRepository repository;
    private final ModelMapper modelMapper;

    /**
     * Retrieves a report by its ID within a company.
     *
     * @param companyId The ID of the company.
     * @param reportId  The ID of the report to retrieve.
     * @return The report entity.
     * @throws ReportNotFoundException if the report with the specified ID is not found.
     */
    public Report getById(UUID companyId, UUID reportId) {
        companyManager.getById(companyId);
        return repository.findById(reportId)
                .orElseThrow(ReportNotFoundException::new);
    }

    /**
     * Saves a new report for a specific company.
     *
     * @param companyId The ID of the company to which the report belongs.
     * @param report    The report entity to save.
     * @return The saved report entity.
     */
    @Transactional
    public Report save(UUID companyId, Report report) {
        Company existingCompany = companyManager.getById(companyId);
        existingCompany.getReports().add(report);
        return report;
    }

    /**
     * Updates an existing report within a company.
     *
     * @param companyId The ID of the company to which the report belongs.
     * @param reportId  The ID of the report to update.
     * @param report    The updated report entity.
     * @return The updated report entity.
     * @throws ReportNotFoundException if the report with the specified ID is not found.
     */
    @Transactional
    public Report update(UUID companyId, UUID reportId, Report report) {
        Report existingReport = getById(companyId, reportId);
        modelMapper.map(report, existingReport);
        return existingReport;
    }

    /**
     * Deletes a report from a company.
     *
     * @param companyId The ID of the company from which the report will be deleted.
     * @param reportId  The ID of the report to delete.
     * @throws ReportNotFoundException if the report with the specified ID is not found.
     */
    public void delete(UUID companyId, UUID reportId) {
        Report report = getById(companyId, reportId);
        repository.delete(report);
    }

}