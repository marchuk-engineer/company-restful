package smida.techtask.exceptions.deletion;

import smida.techtask.exceptions.base.DeletionFailedException;

import java.util.UUID;

public class CompanyDeletionFailedException extends DeletionFailedException {
    public CompanyDeletionFailedException(UUID id) {
        super("Company", id);
    }
}
