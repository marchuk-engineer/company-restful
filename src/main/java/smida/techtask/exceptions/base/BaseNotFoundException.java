package smida.techtask.exceptions.base;

import java.util.UUID;

public class BaseNotFoundException extends RuntimeException {
    private static final String MESSAGE = "%s not found by %s id";

    public BaseNotFoundException(String resourceName, UUID id) {
        super(String.format(MESSAGE, resourceName, id));
    }

}
