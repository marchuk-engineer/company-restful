package smida.techtask.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smida.techtask.entities.Company;
import smida.techtask.entities.Report;
import smida.techtask.entities.ReportDetails;
import smida.techtask.exceptions.deletion.CompanyDeletionFailedException;
import smida.techtask.repositories.managers.CompanyManager;
import smida.techtask.repositories.managers.ReportDetailsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log4j2
public class CompanySagaService {

    private final CompanyManager companyManager;
    private final ReportDetailsManager reportDetailsManager;

    /**
     * Completes the deletion of a company and reports in PostgresSQL
     * as well as reports details in MongoDB step by step.
     * Saga Pattern is used in case when transaction fails,
     * therefore re-insert mechanism is followed.
     *
     * @param existingCompany Company to delete
     */
    @Transactional
    @Retryable(retryFor = {DataAccessException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void deleteCompanyAndAssociatedData(Company existingCompany) {
        List<ReportDetails> deletedReportDetails = new ArrayList<>();
        try {
            tryDelete(existingCompany);

            if (!existingCompany.getReports().isEmpty()) {
                deleteReportsDetails(existingCompany.getReports()
                                .stream()
                                .map(Report::getId)
                                .toList(),
                        deletedReportDetails);
            }
            log.info("Everything related to company with ID {} was deleted successfully", existingCompany.getId());
        } catch (Exception exception) {
            log.error("Failed to delete company with ID {}: {}", existingCompany.getId(), exception.getMessage(), exception);
            handleDeleteFailure(deletedReportDetails);
            throw exception;
        }
    }

    private void tryDelete(Company existingCompany) {
        try {
            deleteCompany(existingCompany);
        } catch (Exception exception) {
            log.error("Company deletion with ID {} failed: {}", existingCompany.getId(), exception.getMessage(), exception);
            throw new CompanyDeletionFailedException(existingCompany.getId());
        }
    }

    private void deleteCompany(Company company) {
        companyManager.deleteById(company.getId());
        log.info("Company with ID {} was deleted", company.getId());
        company.getReports().forEach(report -> log.info("Report with ID {} was deleted", report.getId()));
    }

    private void deleteReportsDetails(List<UUID> reportIds, List<ReportDetails> deletedReportDetails) {
        List<ReportDetails> existingDetails = reportDetailsManager.findAllByIds(reportIds);
        for (ReportDetails detail : existingDetails) {
            reportDetailsManager.delete(detail);
            deletedReportDetails.add(detail);
            log.info("Report detail with ID {} was deleted", detail.getReportId());
        }
    }

    private void handleDeleteFailure(List<ReportDetails> deletedReportDetails) {
        log.info("Trying to rollback ReportDetails");
        deletedReportDetails.forEach(detail -> {
            ReportDetails savedDetails = reportDetailsManager.save(detail);
            log.info("ReportDetails with ID {} was re-inserted", savedDetails.getReportId());
        });
        log.info("ReportDetails were completely re-inserted");
    }

}
