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
import smida.techtask.controllers.CompanyReportsApi;
import smida.techtask.dto.CompanyReportsDto;
import smida.techtask.dto.ReportDto;
import smida.techtask.services.CompanyReportService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.BASE_URL + "/companies/{companyId}/reports")
public class CompanyReportsController implements CompanyReportsApi {

    private static final String RESOURCE_LOCATION = ApiConstants.BASE_URL + "/companies/%s/reports";

    private final CompanyReportService companyReportService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER', 'EDITOR', 'ADMIN')")
    public CompanyReportsDto getAllByCompanyId(@PathVariable UUID companyId) {
        return companyReportService.getAllByCompanyId(companyId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER', 'EDITOR', 'ADMIN')")
    public ReportDto getByCompanyAndReportId(@PathVariable UUID companyId, @PathVariable UUID id) {
        return companyReportService.getById(companyId, id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    public ReportDto createByCompanyId(@PathVariable UUID companyId, @RequestBody @Valid @NotNull ReportDto requestBody, HttpServletResponse response) {
        ReportDto createdReport = companyReportService.createReportById(companyId, requestBody);
        String location = String.format(RESOURCE_LOCATION, companyId).concat("/" + createdReport.getId().toString());
        response.setHeader(HttpHeaders.LOCATION, location);
        return createdReport;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    public ReportDto updateByCompanyAndReportId(@PathVariable UUID companyId, @PathVariable UUID id, @RequestBody @Valid @NotNull ReportDto requestBody) {
        return companyReportService.updateReport(companyId, id, requestBody);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByCompanyAndReportId(@PathVariable UUID companyId, @PathVariable UUID id) {
        companyReportService.deleteReport(companyId, id);
    }

}
