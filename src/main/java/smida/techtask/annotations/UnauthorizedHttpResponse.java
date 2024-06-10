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
 * Custom annotation to define global API responses for unauthorized access.
 * <p>
 * This annotation can be applied to both classes and methods to include
 * a standard response for the 401 Unauthorized status code in the Swagger documentation.
 * <p>
 * It defines the following response:
 * <ul>
 *   <li>401 Unauthorized</li>
 * </ul>
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * @RestController
 * @UnauthorizedHttpResponse
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
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)))
})
public @interface UnauthorizedHttpResponse {
}
