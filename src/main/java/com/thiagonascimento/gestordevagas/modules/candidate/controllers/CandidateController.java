package com.thiagonascimento.gestordevagas.modules.candidate.controllers;

import com.thiagonascimento.gestordevagas.exceptions.UserFoundException;
import com.thiagonascimento.gestordevagas.modules.candidate.entities.CandidateEntity;
import com.thiagonascimento.gestordevagas.modules.candidate.useCases.CreateCandidateUseCase;
import com.thiagonascimento.gestordevagas.modules.candidate.useCases.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @PostMapping("/")
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
    public ResponseEntity<Object> get(HttpServletRequest request) {
        try {
            var id = UUID.fromString(request.getAttribute("candidate_id").toString());
            var profile = this.profileCandidateUseCase.execute(id);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
