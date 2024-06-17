package com.thiagonascimento.gestordevagas.modules.candidate.useCases;

import com.thiagonascimento.gestordevagas.exceptions.JobNotFoundException;
import com.thiagonascimento.gestordevagas.exceptions.UserNotFoundException;
import com.thiagonascimento.gestordevagas.modules.candidate.entities.ApplyJobEntity;
import com.thiagonascimento.gestordevagas.modules.candidate.repositories.ApplyJobRepository;
import com.thiagonascimento.gestordevagas.modules.candidate.repositories.CandidateRepository;
import com.thiagonascimento.gestordevagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private ApplyJobRepository applyJobRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    public ApplyJobEntity execute(UUID candidateId, UUID jobId) {
        this.candidateRepository.findById(candidateId).orElseThrow(UserNotFoundException::new);
        this.jobRepository.findById(jobId).orElseThrow(JobNotFoundException::new);
        var applyJob = ApplyJobEntity.builder()
                .candidateId(candidateId)
                .jobId(jobId)
                .build();
        return this.applyJobRepository.save(applyJob);
    }
}
