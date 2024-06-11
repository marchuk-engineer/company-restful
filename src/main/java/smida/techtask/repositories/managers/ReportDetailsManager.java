package smida.techtask.repositories.managers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smida.techtask.entities.ReportDetails;
import smida.techtask.exceptions.notfound.ReportDetailsNotFoundException;
import smida.techtask.mappers.ReportDetailsMapper;
import smida.techtask.repositories.ReportDetailsRepository;

import java.util.List;
import java.util.UUID;

/**
 * The ReportDetailsManager class manages operations related to ReportDetails entity.
 */
@Service
@RequiredArgsConstructor
public class ReportDetailsManager {

    private final ReportDetailsRepository repository;
    private final ReportDetailsMapper reportDetailsMapper;

    /**
     * Finds report details by ID.
     *
     * @param id The ID of the report details to find.
     * @return The report details with the specified ID.
     * @throws ReportDetailsNotFoundException if the report details with the specified ID is not found.
     */
    public ReportDetails findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ReportDetailsNotFoundException(id));
    }

    /**
     * Retrieves all report details from the repository.
     *
     * @return A list of all report details.
     */
    public List<ReportDetails> findAll() {
        return repository.findAll();
    }

    /**
     * Deletes report details by ID.
     *
     * @param id The ID of the report details to delete.
     */
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    /**
     * Deletes report details.
     *
     * @param reportDetails The report details to delete.
     */
    public void delete(ReportDetails reportDetails) {
        repository.delete(reportDetails);
    }

    /**
     * Retrieves report details by a list of IDs.
     *
     * @param ids The list of IDs of the report details to retrieve.
     * @return A list of report details corresponding to the given IDs.
     */
    public List<ReportDetails> findAllByIds(List<UUID> ids) {
        return repository.findAllById(ids);
    }

    /**
     * Saves report details.
     *
     * @param reportDetails The report details to save.
     * @return The saved report details.
     */
    public ReportDetails save(ReportDetails reportDetails) {
        return repository.save(reportDetails);
    }

    @Transactional
    public ReportDetails update(UUID id, ReportDetails reportDetails) {
        ReportDetails existingDetails = findById(id);
        reportDetailsMapper.update(reportDetails, existingDetails);
        return existingDetails;
    }

}
