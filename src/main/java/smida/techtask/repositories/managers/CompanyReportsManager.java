package smida.techtask.repositories.managers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smida.techtask.entities.Report;
import smida.techtask.exceptions.notfound.ReportNotFoundException;
import smida.techtask.mappers.ReportMapper;
import smida.techtask.repositories.ReportRepository;

import java.util.UUID;

/**
 * Manager class responsible for managing company reports.
 */
@Component
@RequiredArgsConstructor
public class CompanyReportsManager {

    private final ReportRepository repository;
    private final ReportMapper reportMapper;


    /**
     * Saves a new report for a specific company.
     *
     * @param report The report entity to save.
     * @return The saved report entity.
     */
    @Transactional
    public Report save(Report report) {
        return repository.save(report);
    }

    /**
     * Deletes a report from a company.
     *
     * @param companyId The ID of the company from which the report will be deleted.
     * @param reportId  The ID of the report to delete.
     * @throws ReportNotFoundException if the report with the specified ID is not found.
     */
    public void delete(UUID companyId, UUID reportId) {
        Report report = findById(reportId);
        repository.delete(report);
    }

    public Report findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException(id));
    }

}