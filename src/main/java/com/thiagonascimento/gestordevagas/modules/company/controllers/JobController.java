package com.thiagonascimento.gestordevagas.modules.company.controllers;

import com.thiagonascimento.gestordevagas.modules.company.dto.CreateJobDTO;
import com.thiagonascimento.gestordevagas.modules.company.entities.JobEntity;
import com.thiagonascimento.gestordevagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
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
