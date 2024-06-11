package smida.techtask.repositories.managers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import smida.techtask.entities.Report;
import smida.techtask.exceptions.notfound.ReportNotFoundException;
import smida.techtask.repositories.ReportRepository;

import java.util.List;
import java.util.UUID;


/**
 * The ReportManager class manages operations related to Report entity.
 */
@Component
@RequiredArgsConstructor
public class ReportManager {

    private final ReportRepository repository;

    /**
     * Retrieves all reports from the repository.
     *
     * @return A list of all reports.
     */
    public List<Report> findAll() {
        return repository.findAll();
    }

    /**
     * Finds a report by its ID.
     *
     * @param id The ID of the report to find.
     * @return The report with the specified ID.
     * @throws ReportNotFoundException if the report with the specified ID is not found.
     */
    public Report findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException(id));
    }

    /**
     * Deletes a report.
     *
     * @param report The report to delete.
     * @throws ReportNotFoundException if the report with the specified ID is not found.
     */
    public void delete(Report report) {
        this.findById(report.getId());
        repository.delete(report);
    }

}