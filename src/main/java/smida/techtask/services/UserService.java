package smida.techtask.services;

import smida.techtask.entities.User;

import java.util.Optional;

/**
 * UserService interface provides methods for managing user-related operations.
 * It interacts with a {@link smida.techtask.repositories.UserRepository} to perform CRUD operations on users.
 */
public interface UserService {

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return An {@link Optional} containing the user if found, otherwise empty.
     */
    Optional<User> findByUsername(String username);

    /**
     * Saves a user to the database.
     *
     * @param user The user to save.
     * @return The saved user.
     */
    User save(User user);

}
