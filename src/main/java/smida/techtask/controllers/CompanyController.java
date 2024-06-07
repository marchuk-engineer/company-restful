package smida.techtask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import smida.techtask.controllers.dto.CompanyDto;
import smida.techtask.services.CompanyService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
// todo Finish Swagger doc
public class CompanyController {

    private final CompanyService companyService;

    /**
     * Retrieve all companies.
     *
     * @return List of CompanyDto
     */
    @Operation(summary = "Get all companies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved companies")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompanyDto> getAll() {
        return companyService.getAll();
    }

    /**
     * Retrieve a company by its ID.
     *
     * @param id The ID of the company
     * @return CompanyDto
     */
    @Operation(summary = "Get a company by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the company"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompanyDto getById(@PathVariable UUID id) {
        return companyService.getById(id);
    }

    /**
     * Create a new company.
     *
     * @param requestBody The request body containing company details
     * @param response    HttpServletResponse
     * @return CompanyDto
     */
    @Operation(summary = "Create a new company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the company", headers = @Header(name = HttpHeaders.LOCATION, description = "Path to created resource location")),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDto create(@RequestBody @Valid CompanyDto requestBody, HttpServletResponse response) {
        CompanyDto responseBody = companyService.save(requestBody);
        String resourceLocation = String.format("/companies/" + responseBody.getId());
        response.setHeader(HttpHeaders.LOCATION, resourceLocation);
        return responseBody;
    }

    /**
     * Update an existing company.
     *
     * @param id          The ID of the company to be updated
     * @param requestBody The request body containing updated company details
     * @return CompanyDto
     */
    @Operation(summary = "Update an existing company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the company"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompanyDto update(@PathVariable UUID id, @RequestBody @Valid CompanyDto requestBody) {
        return companyService.update(id, requestBody);
    }

    /**
     * Delete a company by its ID.
     *
     * @param id The ID of the company to be deleted
     */
    @Operation(summary = "Delete a company by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the company"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id) {
        companyService.deleteById(id);
    }

}
