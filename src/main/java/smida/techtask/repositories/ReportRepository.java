package smida.techtask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import smida.techtask.entities.Report;

import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {
}
