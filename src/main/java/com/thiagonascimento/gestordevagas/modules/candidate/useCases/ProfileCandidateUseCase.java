package com.thiagonascimento.gestordevagas.modules.candidate.useCases;

import com.thiagonascimento.gestordevagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.thiagonascimento.gestordevagas.modules.candidate.entities.CandidateEntity;
import com.thiagonascimento.gestordevagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID candidateId) {
        var candidate = this.candidateRepository.findById(candidateId).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        return ProfileCandidateResponseDTO.builder()
                .description(candidate.getDescription())
                .email(candidate.getEmail())
                .id(candidate.getId())
                .name(candidate.getName())
                .username(candidate.getUsername())
                .build();
    }
}
