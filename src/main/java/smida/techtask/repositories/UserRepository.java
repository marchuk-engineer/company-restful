package smida.techtask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import smida.techtask.entities.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
