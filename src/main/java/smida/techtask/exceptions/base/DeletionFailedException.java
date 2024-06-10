package smida.techtask.exceptions.base;

import java.util.UUID;

public class DeletionFailedException extends RuntimeException {
    private static final String MESSAGE = "Unable to delete %s  from datasource by %s id ";

    public DeletionFailedException(String resourceName, UUID id) {
        super(String.format(MESSAGE, resourceName, id));
    }

}
