package smida.techtask.exceptions.notfound;

import smida.techtask.exceptions.base.BaseNotFoundException;

import java.util.UUID;

public class CompanyNotFoundException extends BaseNotFoundException {
    public CompanyNotFoundException(UUID id) {
        super("Company", id);
    }
}
