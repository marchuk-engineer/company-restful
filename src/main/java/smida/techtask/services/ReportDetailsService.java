package smida.techtask.services;

import smida.techtask.dto.ReportDetailsDto;

import java.util.List;
import java.util.UUID;


/**
 * Service interface for handling report details operations.
 */
public interface ReportDetailsService {

    /**
     * Retrieves all report details.
     *
     * @return a list of all {@link ReportDetailsDto}.
     */
    List<ReportDetailsDto> getAll();

    /**
     * Retrieves report details by the specified company ID, report ID, and details ID.
     *
     * @param companyId the ID of the company.
     * @param reportId  the ID of the report.
     * @param id        the ID of the report details.
     * @return the {@link ReportDetailsDto} for the specified IDs.
     */
    ReportDetailsDto getByIds(UUID companyId, UUID reportId, UUID id);

    /**
     * Retrieves report details by the specified report ID.
     *
     * @param reportId the ID of the report.
     * @return the {@link ReportDetailsDto} for the specified report ID.
     */
    ReportDetailsDto getByReportId(UUID reportId);

    /**
     * Creates new report details for the specified company ID and report ID.
     *
     * @param companyId        the ID of the company.
     * @param reportId         the ID of the report.
     * @param reportDetailsDto the details to be created.
     * @return the created {@link ReportDetailsDto}.
     */
    ReportDetailsDto create(UUID companyId, UUID reportId, ReportDetailsDto reportDetailsDto);

    /**
     * Updates existing report details for the specified company ID, report ID, and details ID.
     *
     * @param companyId        the ID of the company.
     * @param reportId         the ID of the report.
     * @param uuid             the ID of the report details to be updated.
     * @param reportDetailsDto the new details.
     * @return the updated {@link ReportDetailsDto}.
     */
    ReportDetailsDto update(UUID companyId, UUID reportId, UUID uuid, ReportDetailsDto reportDetailsDto);

    /**
     * Deletes the report details by the specified company ID, report ID, and details ID.
     *
     * @param companyId the ID of the company.
     * @param reportId  the ID of the report.
     * @param uuid      the ID of the report details to be deleted.
     */
    void deleteById(UUID companyId, UUID reportId, UUID uuid);

}


