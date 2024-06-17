package com.thiagonascimento.gestordevagas.modules.company.controllers;

import com.thiagonascimento.gestordevagas.modules.company.dto.CreateJobDTO;
import com.thiagonascimento.gestordevagas.modules.company.entities.JobEntity;
import com.thiagonascimento.gestordevagas.modules.company.useCases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Tag(name = "Vagas", description = "Informações das vagas")
    @Operation(summary = "Cadastro de Vagas", description = "Está função é responsável por cadastrar uma nova vaga.")
    @ApiResponse(
            responseCode = "200",
            description = "Vaga cadastrada com sucesso.",
            content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
            }
    )
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<JobEntity> create(@Valid @RequestBody CreateJobDTO jobDTO, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id").toString();
        var jobEntity = JobEntity.builder()
                .companyId(UUID.fromString(companyId))
                .description(jobDTO.getDescription())
                .benefits(jobDTO.getBenefits())
                .level(jobDTO.getLevel())
                .build();
        return ResponseEntity.ok(this.createJobUseCase.execute(jobEntity));
    }
}
