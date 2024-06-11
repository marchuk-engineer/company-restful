package smida.techtask.controllers.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smida.techtask.constants.ApiConstants;
import smida.techtask.controllers.ReportApi;
import smida.techtask.dto.ReportAndCompanyIdDto;
import smida.techtask.dto.ReportDetailsDto;
import smida.techtask.services.ReportDetailsService;
import smida.techtask.services.ReportService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.BASE_URL)
public class ReportController implements ReportApi {

    private final ReportService reportService;
    private final ReportDetailsService reportDetailsService;

    @GetMapping("/reports")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER', 'EDITOR', 'ADMIN')")
    public List<ReportAndCompanyIdDto> getAll() {
        return reportService.getAll();
    }

    @GetMapping("/reports/{reportId}/details")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER', 'EDITOR', 'ADMIN')")
    public ReportDetailsDto getDetailsByReportId(@PathVariable UUID reportId) {
        return reportDetailsService.getByReportId(reportId);
    }

}
