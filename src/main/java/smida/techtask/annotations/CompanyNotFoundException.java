package smida.techtask.annotations;

public class CompanyNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Company not found";

    public CompanyNotFoundException() {
        super(MESSAGE);
    }
}
