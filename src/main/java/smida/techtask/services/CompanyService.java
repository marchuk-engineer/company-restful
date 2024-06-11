package smida.techtask.services;

import smida.techtask.dto.CompanyDto;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing companies.
 */
public interface CompanyService {

    /**
     * Retrieves all companies.
     *
     * @return a list of CompanyDto representing all companies
     */
    List<CompanyDto> getAll();

    /**
     * Retrieves a company by its ID.
     *
     * @param id the UUID of the company to retrieve
     * @return a CompanyDto representing the company with the given ID
     */
    CompanyDto getById(UUID id);

    /**
     * Saves a new company.
     *
     * @param companyDto the CompanyDto representing the company to save
     * @return a CompanyDto representing the saved company
     */
    CompanyDto save(CompanyDto companyDto);

    /**
     * Updates an existing company.
     *
     * @param id         the UUID of the company to update
     * @param companyDto the CompanyDto representing the updated company details
     * @return a CompanyDto representing the updated company
     */
    CompanyDto update(UUID id, CompanyDto companyDto);

    /**
     * Deletes a company by its ID.
     *
     * @param id the UUID of the company to delete
     */
    void deleteById(UUID id);

}