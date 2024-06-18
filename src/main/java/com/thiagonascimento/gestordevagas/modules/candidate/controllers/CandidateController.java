package com.thiagonascimento.gestordevagas.modules.candidate.controllers;

import com.thiagonascimento.gestordevagas.exceptions.UserFoundException;
import com.thiagonascimento.gestordevagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.thiagonascimento.gestordevagas.modules.candidate.entities.CandidateEntity;
import com.thiagonascimento.gestordevagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import com.thiagonascimento.gestordevagas.modules.candidate.useCases.CreateCandidateUseCase;
import com.thiagonascimento.gestordevagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import com.thiagonascimento.gestordevagas.modules.candidate.useCases.ProfileCandidateUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidate", description = "Informações do candidato")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @PostMapping("/")
    @Operation(
            summary = "Cadastrar Candidato",
            description = "Esta função cadastra um novo candidato."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato cadastrado com sucesso.",
                    content = @Content(schema = @Schema(implementation = CandidateEntity.class))),
            @ApiResponse(responseCode = "400", description = "User already exists")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity request) {
        try {
            var candidate = createCandidateUseCase.execute(request);

            return ResponseEntity.ok(candidate);
        } catch (UserFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(
            summary = "Perfil do Candidato",
            description = "Esta função retorna o perfil do candidato."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil do candidato retornado com sucesso.",
                    content = @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest request) {
        try {
            var id = UUID.fromString(request.getAttribute("candidate_id").toString());
            var profile = this.profileCandidateUseCase.execute(id);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listar vagas por filtro", description = "Listagem de todas as vagas disponíveis por filtro.")
    @ApiResponse(
            responseCode = "200",
            description = "Vagas listadas com sucesso.",
            content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = CandidateEntity.class)))
            }
    )
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getJobs(@RequestParam String filter) {
        try {
            var jobs = this.listAllJobsByFilterUseCase.execute(filter);
            return ResponseEntity.ok(jobs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Inscrição do candidato em uma vaga", description = "Esta função inscreve o candidato em uma vaga.")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID jobId) {
        try {
            var candidateId = UUID.fromString(request.getAttribute("candidate_id").toString());
            var applyJob = this.applyJobCandidateUseCase.execute(candidateId, jobId);
            return ResponseEntity.ok(applyJob);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
