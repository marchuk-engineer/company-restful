package smida.techtask.repositories.managers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import smida.techtask.entities.Report;
import smida.techtask.repositories.ReportRepository;

import java.util.List;

/**
 * Manager class responsible for managing reports.
 */
@Component
@RequiredArgsConstructor
public class ReportManager {

    private final ReportRepository repository;

    /**
     * Retrieves all reports.
     *
     * @return A list of {@link Report} entities representing all reports.
     */
    public List<Report> findAll() {
        return repository.findAll();
    }

}