package smida.techtask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.RequestBody;
import smida.techtask.annotations.JwtAuthParams;
import smida.techtask.annotations.ServerErrorHttpResponses;
import smida.techtask.annotations.UnauthorizedHttpResponse;
import smida.techtask.dto.CompanyDto;
import smida.techtask.dto.security.ErrorDto;

import java.util.List;
import java.util.UUID;

@Tag(name = "Company", description = "Operations")
@JwtAuthParams
@UnauthorizedHttpResponse
@ServerErrorHttpResponses
public interface CompanyApi {

    @Operation(summary = "Get all companies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved companies",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CompanyDto.class)), mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    List<CompanyDto> getAll();

    @Operation(summary = "Get a company by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved the company",
                    content = @Content(schema = @Schema(implementation = CompanyDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(responseCode = "404",
                    description = "Company not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    CompanyDto getById(UUID id);

    @Operation(summary = "Create a new company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Successfully created the company",
                    content = @Content(schema = @Schema(implementation = CompanyDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE),
                    headers = @Header(name = HttpHeaders.LOCATION, description = "Path to created resource location")),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    CompanyDto create(@Valid @NotNull @RequestBody CompanyDto requestBody, HttpServletResponse response);

    @Operation(summary = "Update an existing company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully updated the company",
                    content = @Content(schema = @Schema(implementation = CompanyDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(responseCode = "404",
                    description = "Company not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    CompanyDto update(UUID id, @Valid @NotNull @RequestBody CompanyDto requestBody);

    @Operation(summary = "Delete a company by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the company"),
            @ApiResponse(responseCode = "404",
                    description = "Company not found",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    void deleteById(UUID id);

}
