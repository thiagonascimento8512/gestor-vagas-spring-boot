package com.thiagonascimento.gestordevagas.modules.candidate.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.thiagonascimento.gestordevagas.exceptions.UsernameNotFoundException;
import com.thiagonascimento.gestordevagas.modules.candidate.dto.AuthCandidateRequestDTO;
import com.thiagonascimento.gestordevagas.modules.candidate.dto.AuthCandidateResponseDTO;
import com.thiagonascimento.gestordevagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.jwt.secret.candidate}")
    private String secretKey;

    public ResponseEntity<AuthCandidateResponseDTO> execute(AuthCandidateRequestDTO authCandidateDTO) {
        var candidate = this.candidateRepository.findByUsername(authCandidateDTO.username())
                .orElseThrow(() -> new UsernameNotFoundException("Username/password incorrect"));

        var passwordMatch = this.passwordEncoder.matches(authCandidateDTO.password(), candidate.getPassword());

        if (!passwordMatch) {
            throw new UsernameNotFoundException("Username/password incorrect");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expires_in = Instant.now().plus(Duration.ofHours(2));
        var token = JWT.create()
                .withIssuer("gestordevagas")
                .withExpiresAt(expires_in)
                .withSubject(candidate.getId().toString())
                .withClaim("roles", List.of("candidate"))
                .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO.builder()
                .access_token(token)
                .expires_in(expires_in.toEpochMilli())
                .build();

        return ResponseEntity.ok(authCandidateResponse);
    }
}
