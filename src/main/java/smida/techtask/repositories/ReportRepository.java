package smida.techtask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import smida.techtask.entities.Company;
import smida.techtask.entities.Report;

import java.util.List;
import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {

    List<Report> findAllByCompany(Company company);

}
