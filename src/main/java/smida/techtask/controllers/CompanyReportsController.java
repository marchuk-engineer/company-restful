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
import smida.techtask.dto.CompanyReportsDto;
import smida.techtask.dto.ReportDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies/{companyId}/reports")
public class CompanyReportsController {

    /**
     * Retrieve all reports of a specific company.
     *
     * @param companyId The ID of the company
     * @return List of CompanyReportsDto
     */
    @Operation(summary = "Get all reports of a specific company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reports")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompanyReportsDto> getReportsByCompanyId(@PathVariable UUID companyId) {
        return reportService.getAllByCompanyId(companyId);
    }

    /**
     * Retrieve a report by company ID and report ID.
     *
     * @param companyId The ID of the company
     * @param id        The ID of the report
     * @return ReportDto
     */
    @Operation(summary = "Get a report by company and report ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the report"),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReportDto getByCompanyAndReportId(@PathVariable UUID companyId, @PathVariable UUID id) {
        return reportService.getById(companyId, id);
    }

    /**
     * Create a new report for a specific company.
     *
     * @param companyId   The ID of the company
     * @param requestBody The request body containing report details
     * @param response    HttpServletResponse
     * @return ReportDto
     */
    @Operation(summary = "Create a new report for a specific company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the report", headers = @Header(name = HttpHeaders.LOCATION, description = "Path to created resource location")),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReportDto createByCompanyId(@PathVariable UUID companyId, @RequestBody ReportDto requestBody, HttpServletResponse response) {
        CompanyReportsDto companyReportsDto = reportService.createReportById(companyId, requestBody);
        ReportDto createdReport = companyReportsDto.getReports().getLast();
        String locationSource = String.format("/companies/" + companyId + "/reports/" + createdReport.getId());
        response.setHeader(HttpHeaders.LOCATION, locationSource);
        return createdReport;
    }


    /**
     * Update an existing report for a specific company.
     *
     * @param companyId   The ID of the company
     * @param id          The ID of the report
     * @param requestBody The request body containing updated report details
     * @return ReportDto
     */
    @Operation(summary = "Update an existing report for a specific company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the report"),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReportDto updateByCompanyAndReportId(@PathVariable UUID companyId, @PathVariable UUID id, @RequestBody ReportDto requestBody) {
        return reportService.updateReport(companyId, id, requestBody);
    }

    /**
     * Delete a report by company ID and report ID.
     *
     * @param companyId The ID of the company
     * @param id        The ID of the report to be deleted
     */
    @Operation(summary = "Delete a report by company and report ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the report"),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByCompanyAndReportId(@PathVariable UUID companyId, @PathVariable UUID id) {
        reportService.deleteReport(companyId, id);
    }

}
