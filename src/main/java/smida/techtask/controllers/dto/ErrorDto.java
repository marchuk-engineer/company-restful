package smida.techtask.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(description = "Details about the error")
public class ErrorDto {

    @Schema(description = "Timestamp of when the error occurred", example = "2023-05-23T14:55:27")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timeStamp;

    @Schema(description = "Error message", example = "Full authentication is required to access this resource")
    private String message;

    @Schema(description = "Path of the endpoint that caused the error", example = "/api/users/123")
    private String path;

    @Schema(description = "HTTP status of the error response", example = "UNAUTHORIZED")
    private HttpStatus status;

}
