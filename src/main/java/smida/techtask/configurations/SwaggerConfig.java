package smida.techtask.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "SMIDA API", version = "1.0",
        description = "SMIDA RESTful service",
        contact = @Contact(email = "mishaakamichael999@gmail.com", url = "https://t.me/marchuk_engineer", name = "Mykhailo Marchuk"))
)
public class SwaggerConfig {
}
