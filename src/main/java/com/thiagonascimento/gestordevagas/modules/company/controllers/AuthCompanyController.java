package com.thiagonascimento.gestordevagas.modules.company.controllers;

import com.thiagonascimento.gestordevagas.modules.company.dto.AuthCompanyDTO;
import com.thiagonascimento.gestordevagas.modules.company.useCases.AuthCompanyUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/company")
public class AuthCompanyController {

    @Autowired
    private AuthCompanyUseCase authC;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@Valid @RequestBody AuthCompanyDTO authCompanyDTO) {
        try {
            var token = this.authC.execute(authCompanyDTO);
            return ResponseEntity.ok().body(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
