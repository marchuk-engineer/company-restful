package smida.techtask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smida.techtask.controllers.dto.ErrorDto;
import smida.techtask.controllers.dto.LoginDto;


/**
 * Controller responsible for handling login requests, both basic and social login.
 */
@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Basic and socials login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/sign-in")
    @Operation(summary = "Basic authentication", description = "Authenticate by using username and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User has been authenticated"),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "401",
                    description = "Invalid credentials or such user doesn't exist",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public void basicLogin(@RequestBody @Valid @NotNull LoginDto requestBody,
                           HttpServletResponse response) {
        loginService.loginWithUsernameAndPassword(requestBody, response);
    }

}
