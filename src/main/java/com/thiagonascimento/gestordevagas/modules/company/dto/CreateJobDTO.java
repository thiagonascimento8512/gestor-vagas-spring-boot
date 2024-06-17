package com.thiagonascimento.gestordevagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateJobDTO {
    @Schema(description = "Descrição da vaga", example = "Desenvolvedor Java", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    @Schema(description = "Benefícios da vaga", example = "Vale transporte, vale refeição", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;
    @Schema(description = "Nível da vaga", example = "Júnior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;
}
