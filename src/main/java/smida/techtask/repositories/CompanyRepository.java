package smida.techtask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import smida.techtask.entities.Company;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Company deleteCompanyByRegistrationNumber(String registrationNumber);
}
