package smida.techtask.services;

import smida.techtask.controllers.dto.ReportAndCompanyIdDto;

import java.util.List;

/**
 * Service interface for managing reports.
 */
public interface ReportService {

    /**
     * Retrieves all reports.
     *
     * @return A list of {@link ReportAndCompanyIdDto} DTOs representing all reports.
     */
    List<ReportAndCompanyIdDto> getAll();

}
