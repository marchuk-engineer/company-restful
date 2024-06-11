package smida.techtask.controllers.api;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smida.techtask.constants.ApiConstants;
import smida.techtask.controllers.CompanyApi;
import smida.techtask.dto.CompanyDto;
import smida.techtask.services.CompanyService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.BASE_URL + "/companies/")
public class CompanyController implements CompanyApi {

    private static final String RESOURCE_LOCATION = ApiConstants.BASE_URL + "/companies/";

    private final CompanyService companyService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER', 'EDITOR', 'ADMIN')")
    public List<CompanyDto> getAll() {
        return companyService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER', 'EDITOR', 'ADMIN')")
    public CompanyDto getById(@PathVariable UUID id) {
        return companyService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    public CompanyDto create(@Valid @NotNull CompanyDto requestBody, HttpServletResponse response) {
        CompanyDto responseBody = companyService.save(requestBody);
        String location = RESOURCE_LOCATION.concat(responseBody.getId().toString());
        response.setHeader(HttpHeaders.LOCATION, location);
        return responseBody;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    public CompanyDto update(@PathVariable UUID id, @Valid @NotNull @RequestBody CompanyDto requestBody) {
        return companyService.update(id, requestBody);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteById(@PathVariable UUID id) {
        companyService.deleteById(id);
    }

}
