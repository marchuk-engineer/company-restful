package smida.techtask.controllers.api.security;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smida.techtask.constants.ApiConstants;
import smida.techtask.controllers.AdminApi;
import smida.techtask.dto.security.RegistrationDto;
import smida.techtask.services.AdminService;

@RestController
@RequestMapping(ApiConstants.BASE_URL + "/admin-panel/")
@RequiredArgsConstructor
public class AdminController implements AdminApi {

    private final AdminService adminService;

    @PostMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void createUser(@RequestBody @Valid @NotNull RegistrationDto requestBody) {
        adminService.createUser(requestBody);
    }

    @PostMapping("/editor")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void createEditor(@RequestBody @Valid @NotNull RegistrationDto requestBody) {
        adminService.createEditor(requestBody);
    }

}
