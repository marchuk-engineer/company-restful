package smida.techtask.controllers.handlers;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import smida.techtask.dto.security.ErrorDto;
import smida.techtask.exceptions.base.BaseNotFoundException;
import smida.techtask.exceptions.base.DeletionFailedException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest webRequest) {
        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (FieldError fieldError : fieldErrors) {
            stringJoiner.add(fieldError.getDefaultMessage());
        }
        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now(),
                stringJoiner.toString(),
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(errorDto, status);
    }

    @ExceptionHandler(value = BaseNotFoundException.class)
    public ResponseEntity<ErrorDto> missingRequestHeader(BaseNotFoundException exception, WebRequest webRequest) {
        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = DeletionFailedException.class)
    public ResponseEntity<ErrorDto> unableToDeleteResource(DeletionFailedException exception, WebRequest webRequest) {
        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
