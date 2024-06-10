package smida.techtask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import smida.techtask.annotations.ServerErrorHttpResponses;
import smida.techtask.dto.security.ErrorDto;
import smida.techtask.dto.security.LoginDto;

@Tag(name = "Authentication", description = "Login")
@ServerErrorHttpResponses
public interface LoginApi {

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
    void basicLogin(LoginDto requestBody, HttpServletResponse response);

}
