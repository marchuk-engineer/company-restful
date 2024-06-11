package smida.techtask.services;

import org.springframework.security.authentication.BadCredentialsException;
import smida.techtask.dto.security.LoginDto;
import smida.techtask.entities.User;

/**
 * LoginService interface provides methods for user authentication.
 */
public interface LoginService {

    /**
     * Authenticates a user with the provided username and password.
     *
     * @param loginDto The {@link LoginDto} containing the user's login credentials.
     * @return The authenticated {@link User}.
     * @throws BadCredentialsException if the provided credentials are invalid.
     */
    User loginWithUsernameAndPassword(LoginDto loginDto) throws BadCredentialsException;

}
