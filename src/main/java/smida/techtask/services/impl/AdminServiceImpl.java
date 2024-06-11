package smida.techtask.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import smida.techtask.dto.security.RegistrationDto;
import smida.techtask.dto.security.Role;
import smida.techtask.services.AdminService;
import smida.techtask.services.RegistrationService;

@Service
@RequiredArgsConstructor
@Log4j2
public class AdminServiceImpl implements AdminService {

    private final RegistrationService registrationService;

    @Override
    public void createEditor(RegistrationDto dto) {
        registrationService.register(dto, Role.EDITOR);
    }

    @Override
    public void createUser(RegistrationDto dto) {
        registrationService.register(dto, Role.USER);
    }

}
