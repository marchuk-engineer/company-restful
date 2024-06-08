package smida.techtask.exceptions;

import java.util.UUID;

public class ReportDetailsNotFoundException extends BaseNotFoundException {

    public ReportDetailsNotFoundException(UUID id) {
        super("Report Details", id);
    }
}
