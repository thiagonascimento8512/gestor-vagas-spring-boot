package com.thiagonascimento.gestordevagas.modules.company.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.thiagonascimento.gestordevagas.modules.company.dto.AuthCompanyDTO;
import com.thiagonascimento.gestordevagas.modules.company.dto.AuthCompanyResponseDTO;
import com.thiagonascimento.gestordevagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthCompanyUseCase {

    @Value("${security.jwt.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<AuthCompanyResponseDTO> execute(AuthCompanyDTO authCompanyDTO) {
        var company = this.companyRepository
                .findByUsername(authCompanyDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if (!passwordMatches) {
            throw new RuntimeException("Senha inválida");
        }

        var expires_in = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("gestordevagas")
                .withExpiresAt(expires_in)
                .withSubject(company.getId().toString())
                .withClaim("role", List.of("COMPANY"))
                .sign(Algorithm.HMAC256(secretKey));

        AuthCompanyResponseDTO responseDTO = AuthCompanyResponseDTO.builder()
                .access_token(token)
                .expires_in(expires_in.toEpochMilli())
                .build();

        return ResponseEntity.ok(responseDTO);
    }
}
