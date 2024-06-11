package smida.techtask.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smida.techtask.entities.Report;
import smida.techtask.entities.ReportDetails;
import smida.techtask.exceptions.deletion.ReportDeletionFailedException;
import smida.techtask.repositories.managers.ReportDetailsManager;
import smida.techtask.repositories.managers.ReportManager;

@Component
@RequiredArgsConstructor
@Log4j2
public class ReportSagaService {

    private final ReportManager reportManager;
    private final ReportDetailsManager reportDetailsManager;

    @Retryable(retryFor = {DataAccessException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    @Transactional
    public void deleteReportAndDetails(Report existingReport) {
        try {
            tryDeleteReport(existingReport);
            tryDeleteReportDetails(existingReport);

            log.info("Report and details with ID {} was successfully deleted", existingReport.getId());
        } catch (Exception exception) {
            log.error("Failed to delete Report with ID {}: {}", existingReport.getId(), exception.getMessage(), exception);
        }
    }

    private void tryDeleteReportDetails(Report existingReport) {
        try {
            ReportDetails reportDetails = reportDetailsManager.findById(existingReport.getId());
            reportDetailsManager.delete(reportDetails);
        } catch (Exception exception) {
            log.info("Unable to delete ReportDetails by {} ID {}", existingReport.getId(), exception.getMessage(), exception);
        }
    }

    private void tryDeleteReport(Report existingReport) {
        try {
            reportManager.delete(existingReport);
        } catch (Exception exception) {
            log.error("Report deletion with ID {} failed: {}", existingReport.getId(), exception.getMessage(), exception);
            throw new ReportDeletionFailedException(existingReport.getId());
        }
    }

}
