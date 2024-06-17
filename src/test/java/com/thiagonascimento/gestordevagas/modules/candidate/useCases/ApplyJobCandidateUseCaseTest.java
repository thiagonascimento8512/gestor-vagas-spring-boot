package com.thiagonascimento.gestordevagas.modules.candidate.useCases;

import com.thiagonascimento.gestordevagas.exceptions.JobNotFoundException;
import com.thiagonascimento.gestordevagas.exceptions.UserNotFoundException;
import com.thiagonascimento.gestordevagas.modules.candidate.entities.ApplyJobEntity;
import com.thiagonascimento.gestordevagas.modules.candidate.entities.CandidateEntity;
import com.thiagonascimento.gestordevagas.modules.candidate.repositories.ApplyJobRepository;
import com.thiagonascimento.gestordevagas.modules.candidate.repositories.CandidateRepository;
import com.thiagonascimento.gestordevagas.modules.company.entities.JobEntity;
import com.thiagonascimento.gestordevagas.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply for a job if the candidate does not exist")
    public void shouldNotBeAbleToApplyForAJobIfTheCandidateDoesNotExist() {
        try {
            applyJobCandidateUseCase.execute(UUID.randomUUID(), UUID.randomUUID());
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to apply for a job if the job does not exist")
    public void shouldNotBeAbleToApplyForAJobIfTheJobDoesNotExist() {
        var idCandidate = UUID.randomUUID();
        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        try {
            applyJobCandidateUseCase.execute(idCandidate, UUID.randomUUID());
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should be able to apply for a job")
    public void shouldBeAbleToApplyForAJob() {
        var idCandidate = UUID.randomUUID();
        var idJob = UUID.randomUUID();

        var applyJob = ApplyJobEntity.builder()
                .candidateId(idCandidate)
                .jobId(idJob)
                .build();

        var applyJobCreated = ApplyJobEntity.builder()
                .id(UUID.randomUUID())
                .candidateId(idCandidate)
                .jobId(idJob)
                .build();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));
        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
        assertThat(result).hasFieldOrPropertyWithValue("candidateId", idCandidate);
        assertThat(result).hasFieldOrPropertyWithValue("jobId", idJob);
    }
}
