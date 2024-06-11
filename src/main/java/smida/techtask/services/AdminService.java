package smida.techtask.services;

import smida.techtask.dto.security.RegistrationDto;

/**
 * AdminService interface provides methods for administrative tasks such as creating users and editors.
 * These methods delegate the registration process to the {@link RegistrationService}.
 */
public interface AdminService {

    /**
     * Creates an editor with the provided registration information.
     *
     * @param dto The {@link RegistrationDto} containing the editor's registration details.
     */
    void createEditor(RegistrationDto dto);

    /**
     * Creates a user with the provided registration information.
     *
     * @param dto The {@link RegistrationDto} containing the user's registration details.
     */
    void createUser(RegistrationDto dto);

}
