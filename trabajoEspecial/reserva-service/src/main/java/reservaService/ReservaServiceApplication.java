package reservaService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"controller", "service", "repository", "models", "reservaService"})
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "models")
public class ReservaServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReservaServiceApplication.class, args);
    }
}