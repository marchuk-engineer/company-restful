package smida.techtask.annotations;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import smida.techtask.dto.security.ErrorDto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to define global API responses for server errors.
 * <p>
 * This annotation can be applied to both classes and methods to include
 * standard responses for various 5xx server error status codes in the Swagger documentation.
 *
 * <p>It defines the following responses:
 * <ul>
 *   <li>500 Internal Server Error</li>
 *   <li>501 Not Implemented</li>
 *   <li>502 Bad Gateway</li>
 *   <li>503 Service Unavailable</li>
 *   <li>504 Gateway Timeout</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>
 * {@code
 * @RestController
 * @GlobalApiResponses
 * public class MyController {
 *
 *     @GetMapping("/example")
 *     public String exampleEndpoint() {
 *         return "Example response";
 *     }
 * }
 * }
 * </pre>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))),
        @ApiResponse(responseCode = "501", description = "Not Implemented", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))),
        @ApiResponse(responseCode = "502", description = "Bad Gateway", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))),
        @ApiResponse(responseCode = "504", description = "Gateway Timeout", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)))
})
public @interface ServerErrorHttpResponses {
}