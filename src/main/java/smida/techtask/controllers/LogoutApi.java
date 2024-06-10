package smida.techtask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import smida.techtask.annotations.ServerErrorHttpResponses;

@Tag(name = "Log out", description = "Log out authenticated user")
@ServerErrorHttpResponses
public interface LogoutApi {

    @Operation(summary = "Log out a user",
            description = "Logs out the authenticated user by clearing the authentication cookies",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully logged out",
                            content = @Content(schema = @Schema(hidden = true)))
            }
    )
    void logout(HttpServletRequest request, HttpServletResponse response);

}
