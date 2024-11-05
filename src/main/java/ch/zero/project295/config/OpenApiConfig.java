package ch.zero.project295.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Doc for Note API",
                version = "1.0",
                description = "API documentation for my Spring Boot project"
        )
)
public class OpenApiConfig {
    // Additional configuration if needed
}
