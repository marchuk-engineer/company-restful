package smida.techtask.controllers.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smida.techtask.constants.ApiConstants;
import smida.techtask.controllers.CompanyReportDetailsApi;
import smida.techtask.dto.ReportDetailsDto;
import smida.techtask.services.ReportDetailsService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.BASE_URL + "/companies/{companyId}/reports/{reportId}/details")
public class CompanyReportDetailsController implements CompanyReportDetailsApi {

    private final ReportDetailsService reportDetailsService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER', 'EDITOR', 'ADMIN')")
    public ReportDetailsDto getById(@PathVariable UUID companyId, @PathVariable UUID reportId, @PathVariable UUID id) {
        return reportDetailsService.getByIds(companyId, reportId, id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    public ReportDetailsDto create(@PathVariable UUID companyId, @PathVariable UUID reportId, @Valid @NotNull @RequestBody ReportDetailsDto requestBody) {
        return reportDetailsService.create(companyId, reportId, requestBody);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
    public ReportDetailsDto update(@PathVariable UUID companyId, @PathVariable UUID reportId, @PathVariable UUID id, @Valid @NotNull @RequestBody ReportDetailsDto requestBody) {
        return reportDetailsService.update(companyId, reportId, id, requestBody);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID companyId, @PathVariable UUID reportId, @PathVariable UUID id) {
        reportDetailsService.deleteById(companyId, reportId, id);
    }

}
