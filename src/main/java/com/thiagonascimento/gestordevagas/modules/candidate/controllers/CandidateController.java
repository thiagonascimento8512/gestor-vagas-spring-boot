package com.thiagonascimento.gestordevagas.modules.candidate.controllers;

import com.thiagonascimento.gestordevagas.exceptions.UserFoundException;
import com.thiagonascimento.gestordevagas.modules.candidate.entities.CandidateEntity;
import com.thiagonascimento.gestordevagas.modules.candidate.useCases.CreateCandidateUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity request) {
        try {
            var candidate = createCandidateUseCase.execute(request);

            return ResponseEntity.ok(candidate);
        } catch (UserFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
