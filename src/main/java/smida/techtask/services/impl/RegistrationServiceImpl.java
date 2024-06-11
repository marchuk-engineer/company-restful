package smida.techtask.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import smida.techtask.dto.security.RegistrationDto;
import smida.techtask.dto.security.Role;
import smida.techtask.entities.User;
import smida.techtask.exceptions.UsernameAlreadyTakenException;
import smida.techtask.mappers.UserMapper;
import smida.techtask.services.RegistrationService;
import smida.techtask.services.UserService;


@Service
@RequiredArgsConstructor
@Log4j2
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegistrationDto dto, Role role) {
        userService.findByUsername(dto.getUsername())
                .ifPresentOrElse(
                        user -> {
                            throw new UsernameAlreadyTakenException();
                        },
                        () -> createNew(dto, role)
                );
    }

    private void createNew(RegistrationDto dto, Role role) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User newUser = userMapper.toEntity(dto);
        newUser.setRole(role);
        userService.save(newUser);
        log.info("User with ID: {} has successfully registered.", newUser.getId());
    }

}
