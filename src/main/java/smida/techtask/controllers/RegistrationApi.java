package smida.techtask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import smida.techtask.annotations.ServerErrorHttpResponses;
import smida.techtask.dto.security.ErrorDto;
import smida.techtask.dto.security.RegistrationDto;


@Tag(name = "Registration")
@ServerErrorHttpResponses
public interface RegistrationApi {

    String USERNAME_IS_ALREADY_TAKEN_MESSAGE = "We’re sorry. This username already taken.";
    String USER_REGISTERED_SUCCESSFULLY_MESSAGE = "User registered successfully.";

    @PostMapping("/sign-up")
    @Operation(summary = "Register a user",
            description = "Attempt to sign up new user",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = USER_REGISTERED_SUCCESSFULLY_MESSAGE + "\t\n"
                                    + USERNAME_IS_ALREADY_TAKEN_MESSAGE),
                    @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            })
    void signUp(@RequestBody @Valid @NotNull RegistrationDto requestBody);

}
