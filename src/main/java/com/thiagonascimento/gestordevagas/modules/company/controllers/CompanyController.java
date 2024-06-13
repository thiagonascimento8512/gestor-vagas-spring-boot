package com.thiagonascimento.gestordevagas.modules.company.controllers;

import com.thiagonascimento.gestordevagas.modules.candidate.useCases.CreateCandidateUseCase;
import com.thiagonascimento.gestordevagas.modules.company.dto.AuthCompanyDTO;
import com.thiagonascimento.gestordevagas.modules.company.entities.CompanyEntity;
import com.thiagonascimento.gestordevagas.modules.company.useCases.AuthCompanyUseCase;
import com.thiagonascimento.gestordevagas.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity company) {
        try {
            return ResponseEntity.ok(this.createCompanyUseCase.execute(company));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
