package smida.techtask.repositories.managers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smida.techtask.entities.ReportDetails;
import smida.techtask.repositories.ReportDetailsRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportDetailsManager {

    private final ReportDetailsRepository repository;

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public void delete(ReportDetails reportDetails) {
        repository.delete(reportDetails);
    }

    public List<ReportDetails> findAllByIds(List<UUID> ids) {
        return repository.findAllById(ids);
    }

    public ReportDetails save(ReportDetails reportDetails) {
        return repository.save(reportDetails);
    }

}
