package com.thiagonascimento.gestordevagas.modules.company.controllers;

import com.thiagonascimento.gestordevagas.modules.company.entities.JobEntity;
import com.thiagonascimento.gestordevagas.modules.company.useCases.CreateJobUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public ResponseEntity<JobEntity> create(@Valid @RequestBody JobEntity job) {
        return ResponseEntity.ok(this.createJobUseCase.execute(job));
    }
}
