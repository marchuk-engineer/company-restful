package smida.techtask.annotations;

public class ReportNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Report not found";

    public ReportNotFoundException() {
        super(MESSAGE);
    }
}
