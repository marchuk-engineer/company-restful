package smida.techtask.exceptions.notfound;

import smida.techtask.exceptions.base.BaseNotFoundException;

import java.util.UUID;

public class ReportNotFoundException extends BaseNotFoundException {
    public ReportNotFoundException(UUID id) {
        super("Report", id);
    }
}
