package smida.techtask.controllers.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RegistrationDto {

    @NotBlank(message = "Invalid Login: Empty login")
    @NotNull(message = "Invalid Login: Login is NULL")
    @Size(min = 5, max = 30, message = "Invalid Login: Must be of 5 - 30 characters")
    private String username;

    @NotBlank(message = "Empty password")
    @NotNull(message = "Password is NULL")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,30}$", message = "Password must be of 8 - 30 characters and contain at least one letter and one number")
    private String password;

}
