package com.threego.algomemberservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Algo - API 명세서",
                version = "v1.0.0"
        )
)
@Configuration
public class SwaggerConfig {
        @Bean
        public OpenAPI openAPI() {
                return new OpenAPI()
                        .addSecurityItem(new SecurityRequirement().addList("Authentication"))
                        .components(new Components()
                                .addSecuritySchemes("Authentication",
                                        new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer").bearerFormat("JWT"))
                        );
        }
}