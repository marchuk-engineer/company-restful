package smida.techtask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import smida.techtask.annotations.JwtAuthParams;
import smida.techtask.annotations.ServerErrorHttpResponses;
import smida.techtask.annotations.UnauthorizedHttpResponse;
import smida.techtask.dto.security.ErrorDto;
import smida.techtask.dto.security.RegistrationDto;

@Tag(name = "Admin", description = "Operations via admin panel")
@JwtAuthParams
@UnauthorizedHttpResponse
@ServerErrorHttpResponses
public interface AdminApi {
    String USERNAME_IS_ALREADY_TAKEN_MESSAGE = "We’re sorry. This username already taken.";
    String USER_REGISTERED_SUCCESSFULLY_MESSAGE = "User registered successfully.";
    String CLIENT_REGISTERED_SUCCESSFULLY_MESSAGE = "Editor registered successfully.";

    @Operation(summary = "Register a user",
            description = "Attempt to sign up new user",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = USER_REGISTERED_SUCCESSFULLY_MESSAGE + "\t\n"
                                    + USERNAME_IS_ALREADY_TAKEN_MESSAGE),
                    @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            })
    void createUser(@RequestBody @Valid @NotNull RegistrationDto requestBody);

    @Operation(summary = "Register a editor",
            description = "Attempt to sign up new editor",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = CLIENT_REGISTERED_SUCCESSFULLY_MESSAGE + "\t\n"
                                    + USERNAME_IS_ALREADY_TAKEN_MESSAGE),
                    @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            })
    void createEditor(@RequestBody @Valid @NotNull RegistrationDto requestBody);

}
