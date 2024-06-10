package smida.techtask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import smida.techtask.annotations.JwtAuthParams;
import smida.techtask.annotations.ServerErrorHttpResponses;
import smida.techtask.annotations.UnauthorizedHttpResponse;
import smida.techtask.dto.ReportAndCompanyIdDto;
import smida.techtask.dto.ReportDetailsDto;
import smida.techtask.dto.security.ErrorDto;

import java.util.List;
import java.util.UUID;

@Tag(name = "Report", description = "Operations")
@JwtAuthParams
@UnauthorizedHttpResponse
@ServerErrorHttpResponses
public interface ReportApi {

    @Operation(summary = "Get all reports")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved reports",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReportAndCompanyIdDto.class)), mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    List<ReportAndCompanyIdDto> getAll();

    @Operation(summary = "Get report details of a report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved report details",
                    content = @Content(schema = @Schema(implementation = ReportDetailsDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(responseCode = "404",
                    description = "Report not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    ReportDetailsDto getDetailsByReportId(UUID reportId);

}
