package com.thiagonascimento.gestordevagas;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Gestor de Vagas",
                version = "1.0",
                description = "API para gestão de vagas de emprego"
        )
)
public class GestorDeVagasApplication {
    public static void main(String[] args) {
        SpringApplication.run(GestorDeVagasApplication.class, args);
    }
}
