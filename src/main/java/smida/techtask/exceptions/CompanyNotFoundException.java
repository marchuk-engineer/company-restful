package smida.techtask.exceptions;

import java.util.UUID;

public class CompanyNotFoundException extends BaseNotFoundException {
    public CompanyNotFoundException(UUID id) {
        super("Company", id);
    }
}
