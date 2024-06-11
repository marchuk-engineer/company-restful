package smida.techtask.exceptions.notfound;

import smida.techtask.exceptions.base.BaseNotFoundException;

import java.util.UUID;

public class ReportDetailsNotFoundException extends BaseNotFoundException {

    public ReportDetailsNotFoundException(UUID id) {
        super("Report Details", id);
    }
}
