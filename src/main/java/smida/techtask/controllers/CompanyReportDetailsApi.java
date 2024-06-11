package smida.techtask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import smida.techtask.annotations.JwtAuthParams;
import smida.techtask.annotations.ServerErrorHttpResponses;
import smida.techtask.annotations.UnauthorizedHttpResponse;
import smida.techtask.dto.CompanyDto;
import smida.techtask.dto.ReportDetailsDto;
import smida.techtask.dto.security.ErrorDto;

import java.util.UUID;

@Tag(name = "Report details", description = "Operations")
@JwtAuthParams
@UnauthorizedHttpResponse
@ServerErrorHttpResponses
public interface CompanyReportDetailsApi {

    @Operation(summary = "Get report details of a report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved report details",
                    content = @Content(schema = @Schema(implementation = ReportDetailsDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(responseCode = "404",
                    description = "Report details not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    ReportDetailsDto getById(@PathVariable UUID companyId, @PathVariable UUID reportId, @PathVariable UUID id);

    @Operation(summary = "Create a report details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Successfully created the report details",
                    content = @Content(schema = @Schema(implementation = CompanyDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE),
                    headers = @Header(name = HttpHeaders.LOCATION, description = "Path to created resource location")),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    ReportDetailsDto create(@PathVariable UUID companyId, @PathVariable UUID reportId, @Valid @NotNull @RequestBody ReportDetailsDto requestBody);

    @Operation(summary = "Update an existing report details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully updated the report details",
                    content = @Content(schema = @Schema(implementation = CompanyDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(responseCode = "404",
                    description = "Report details not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    ReportDetailsDto update(@PathVariable UUID companyId, @PathVariable UUID reportId, @PathVariable UUID id, @Valid @NotNull @RequestBody ReportDetailsDto requestBody);

    @Operation(summary = "Delete a report details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully the report details"),
            @ApiResponse(responseCode = "404",
                    description = "Report details not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    void delete(@PathVariable UUID companyId, @PathVariable UUID reportId, @PathVariable UUID id);

}
