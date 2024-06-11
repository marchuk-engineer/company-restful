package smida.techtask.exceptions.deletion;

import smida.techtask.exceptions.base.DeletionFailedException;

import java.util.UUID;

public class ReportDeletionFailedException extends DeletionFailedException {
    public ReportDeletionFailedException(UUID id) {
        super("Report", id);
    }
}
