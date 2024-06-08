package smida.techtask.services;

import smida.techtask.controllers.dto.CompanyReportsDto;
import smida.techtask.controllers.dto.ReportDto;

import java.util.UUID;

/**
 * Service interface for managing company reports.
 */
public interface CompanyReportService {

    /**
     * Retrieves all reports of a company.
     *
     * @param companyId The ID of the company.
     * @return A DTO containing all reports of the specified company.
     */
    CompanyReportsDto getAllByCompanyId(UUID companyId);

    /**
     * Retrieves a specific report by its ID within a company.
     *
     * @param companyId The ID of the company.
     * @param reportId  The ID of the report to retrieve.
     * @return The DTO representation of the specified report.
     */
    ReportDto getById(UUID companyId, UUID reportId);

    /**
     * Creates a new report for a company.
     *
     * @param companyId The ID of the company to which the report belongs.
     * @param reportDto The DTO representation of the report to create.
     * @return The DTO representation of the created report.
     */
    ReportDto createReportById(UUID companyId, ReportDto reportDto);

    /**
     * Updates an existing report within a company.
     *
     * @param companyId The ID of the company to which the report belongs.
     * @param reportId  The ID of the report to update.
     * @param reportDto The DTO representation of the updated report.
     * @return The DTO representation of the updated report.
     */
    ReportDto updateReport(UUID companyId, UUID reportId, ReportDto reportDto);

    /**
     * Deletes a report from a company.
     *
     * @param companyId The ID of the company from which the report will be deleted.
     * @param reportId  The ID of the report to delete.
     */
    void deleteReport(UUID companyId, UUID reportId);

}
