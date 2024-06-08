package smida.techtask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import smida.techtask.controllers.dto.CompanyReportsDto;
import smida.techtask.controllers.dto.ReportDto;
import smida.techtask.services.CompanyReportService;

import java.util.UUID;

/**
 * Controller for managing reports of a specific company.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/companies/{companyId}/reports")
public class CompanyReportsController {

    private final CompanyReportService companyReportService;

    /**
     * Retrieves all reports of a specific company.
     *
     * @param companyId The ID of the company.
     * @return The DTO representation of all reports of the specified company.
     */
    @Operation(summary = "Get all reports of a specific company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reports")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CompanyReportsDto getReportsByCompanyId(@PathVariable UUID companyId) {
        return companyReportService.getAllByCompanyId(companyId);
    }

    /**
     * Retrieves a report by its ID within a company.
     *
     * @param companyId The ID of the company.
     * @param id        The ID of the report to retrieve.
     * @return The DTO representation of the specified report.
     */
    @Operation(summary = "Get a report by company and report ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the report"),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReportDto getByCompanyAndReportId(@PathVariable UUID companyId, @PathVariable UUID id) {
        return companyReportService.getById(companyId, id);
    }

    /**
     * Creates a new report for a specific company.
     *
     * @param companyId   The ID of the company to which the report belongs.
     * @param requestBody The DTO representation of the report to create.
     * @param response    The HTTP servlet response object.
     * @return The DTO representation of the created report.
     */
    @Operation(summary = "Create a new report for a specific company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the report", headers = @Header(name = HttpHeaders.LOCATION, description = "Path to created resource location")),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReportDto createByCompanyId(@PathVariable UUID companyId, @RequestBody ReportDto requestBody, HttpServletResponse response) {
        ReportDto createdReport = companyReportService.createReportById(companyId, requestBody);
        String locationSource = String.format("/companies/%s/reports/%s", companyId, createdReport.getId());
        response.setHeader(HttpHeaders.LOCATION, locationSource);
        return createdReport;
    }

    /**
     * Updates an existing report within a company.
     *
     * @param companyId   The ID of the company to which the report belongs.
     * @param id          The ID of the report to update.
     * @param requestBody The DTO representation of the updated report.
     * @return The DTO representation of the updated report.
     */
    @Operation(summary = "Update an existing report for a specific company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the report"),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReportDto updateByCompanyAndReportId(@PathVariable UUID companyId, @PathVariable UUID id, @RequestBody ReportDto requestBody) {
        return companyReportService.updateReport(companyId, id, requestBody);
    }

    /**
     * Deletes a report from a company.
     *
     * @param companyId The ID of the company from which the report will be deleted.
     * @param id        The ID of the report to delete.
     */
    @Operation(summary = "Delete a report by company and report ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the report"),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByCompanyAndReportId(@PathVariable UUID companyId, @PathVariable UUID id) {
        companyReportService.deleteReport(companyId, id);
    }

}
