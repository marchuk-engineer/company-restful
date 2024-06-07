package smida.techtask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import smida.techtask.controllers.dto.ReportDetailsDto;
import smida.techtask.controllers.dto.ReportDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    /**
     * Retrieve all reports.
     *
     * @return List of ReportDto
     */
    @Operation(summary = "Get all reports")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reports")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReportDto> getAll() {
        return reportService.getAll();
    }

    /**
     * Retrieve detailed information of a report.
     *
     * @param id The ID of the report
     * @return ReportDetailsDto
     */
    @Operation(summary = "Get detailed information of a report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved report details"),
            @ApiResponse(responseCode = "404", description = "Report details not found")
    })
    @GetMapping("/{id}/details")
    @ResponseStatus(HttpStatus.OK)
    public ReportDetailsDto getReportDetails(@PathVariable UUID id) {
        return reportService.getReportDetails(id);
    }

}
