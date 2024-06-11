package smida.techtask.services;

import smida.techtask.dto.security.RegistrationDto;
import smida.techtask.dto.security.Role;
import smida.techtask.exceptions.UsernameAlreadyTakenException;

/**
 * RegistrationService interface provides methods for registration.
 */
public interface RegistrationService {

    /**
     * Registers a new user with the provided registration information and role.
     *
     * @param dto  The {@link RegistrationDto} containing the user's registration details.
     * @param role The role of the user being registered.
     * @throws UsernameAlreadyTakenException if the provided username is already taken.
     */
    void register(RegistrationDto dto, Role role) throws UsernameAlreadyTakenException;

}
