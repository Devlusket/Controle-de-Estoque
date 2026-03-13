package com.controle.estoque.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {


  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Controle de Estoque API")
            .description("API para controle de estoque regional")
            .version("1.0.0"))
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        .components(new Components()
      .addSecuritySchemes("bearerAuth", new SecurityScheme()
          .type(SecurityScheme.Type.HTTP)
          .scheme("bearer")
          .bearerFormat("JWT")));
  }






}
