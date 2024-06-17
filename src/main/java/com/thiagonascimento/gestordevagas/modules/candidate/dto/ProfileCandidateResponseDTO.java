package com.thiagonascimento.gestordevagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {
    @Schema(description = "Descrição do candidato", example = "Desenvolvedor Java")
    private String description;
    @Schema(description = "Username do candidato", example = "thiagonascimento")
    private String username;
    @Schema(description = "Email do candidato", example = "thiago@gmail.com")
    private String email;
    private UUID id;
    @Schema(description = "Nome do candidato", example = "Thiago Nascimento")
    private String name;
}
