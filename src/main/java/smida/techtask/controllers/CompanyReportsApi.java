package smida.techtask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import smida.techtask.annotations.JwtAuthParams;
import smida.techtask.annotations.ServerErrorHttpResponses;
import smida.techtask.annotations.UnauthorizedHttpResponse;
import smida.techtask.dto.CompanyReportsDto;
import smida.techtask.dto.ReportDto;
import smida.techtask.dto.security.ErrorDto;

import java.util.UUID;

@Tag(name = "Company reports", description = "Operations")
@JwtAuthParams
@UnauthorizedHttpResponse
@ServerErrorHttpResponses
public interface CompanyReportsApi {

    @Operation(summary = "Get all reports of a specific company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved reports",
                    content = @Content(schema = @Schema(implementation = CompanyReportsDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(responseCode = "404",
                    description = "Company not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    CompanyReportsDto getAllByCompanyId(@PathVariable UUID companyId);

    @Operation(summary = "Get a report by company and report ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved the report",
                    content = @Content(schema = @Schema(implementation = ReportDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(responseCode = "404",
                    description = "Report not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    ReportDto getByCompanyAndReportId(@PathVariable UUID companyId, @PathVariable UUID id);

    @Operation(summary = "Create a new report for a specific company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Successfully created the report",
                    headers = @Header(name = HttpHeaders.LOCATION, description = "Path to created resource location"),
                    content = @Content(schema = @Schema(implementation = ReportDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    ReportDto createByCompanyId(@PathVariable UUID companyId, @RequestBody @Valid @NotNull ReportDto requestBody, HttpServletResponse response);

    @Operation(summary = "Update an existing report for a specific company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully updated the report",
                    content = @Content(schema = @Schema(implementation = ReportDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(responseCode = "404",
                    description = "Report not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    ReportDto updateByCompanyAndReportId(@PathVariable UUID companyId, @PathVariable UUID id, @RequestBody @Valid @NotNull ReportDto requestBody);

    @Operation(summary = "Delete a report by company and report ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the report"),
            @ApiResponse(responseCode = "404",
                    description = "Report not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    void deleteByCompanyAndReportId(@PathVariable UUID companyId, @PathVariable UUID id);

}
