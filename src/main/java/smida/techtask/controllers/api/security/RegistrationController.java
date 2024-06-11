package smida.techtask.controllers.api.security;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import smida.techtask.constants.ApiConstants;
import smida.techtask.controllers.RegistrationApi;
import smida.techtask.dto.security.RegistrationDto;
import smida.techtask.dto.security.Role;
import smida.techtask.services.RegistrationService;

/**
 * REST controller for handling user registration.
 */
@RestController
@RequestMapping(ApiConstants.BASE_URL + "/auth/sign-up")
@RequiredArgsConstructor
public class RegistrationController implements RegistrationApi {

    private final RegistrationService registrationService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void userSignUp(@RequestBody @Valid @NotNull RegistrationDto requestBody) {
        registrationService.register(requestBody, Role.USER);
    }

}
