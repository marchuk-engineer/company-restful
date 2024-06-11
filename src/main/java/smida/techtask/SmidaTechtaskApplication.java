package smida.techtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaRepositories(basePackages = "smida.techtask.repositories")
@EnableJpaAuditing
public class SmidaTechtaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmidaTechtaskApplication.class, args);
    }

}
