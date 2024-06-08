package smida.techtask.exceptions;

import java.util.UUID;

public class ReportNotFoundException extends BaseNotFoundException {
    public ReportNotFoundException(UUID id) {
        super("Report", id);
    }
}
