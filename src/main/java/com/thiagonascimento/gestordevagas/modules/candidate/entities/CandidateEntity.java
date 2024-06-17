package com.thiagonascimento.gestordevagas.modules.candidate.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "candidate")
@Data
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(description = "Nome do candidato", example = "Thiago Nascimento", required = true)
    private String name;

    @Email(message = "Email should be valid")
    @Schema(description = "Email do candidato", example = "thiago@gmail.com", required = true)
    private String email;

    @Pattern(regexp = "\\S+", message = "Username should not have spaces")
    @Schema(description = "Username do candidato", example = "thiagonasc", required = true)
    private String username;

    @Length(min = 6, max = 100, message = "Password should be between 6 and 100 characters")
    @Schema(description = "Senha do candidato", example = "123456", format = "password", minLength = 6, maxLength = 10, required = true)
    private String password;

    @Schema(description = "Desenvolvedor Frontend", example = "Desenvolvedor Frontend")
    private String description;

    @Schema(description = "Link para o currículo do candidato", example = "https://www.linkedin.com/in/thiagonasc/")
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
