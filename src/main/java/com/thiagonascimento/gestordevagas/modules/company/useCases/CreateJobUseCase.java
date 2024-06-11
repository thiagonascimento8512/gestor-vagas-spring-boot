package com.thiagonascimento.gestordevagas.modules.company.useCases;

import com.thiagonascimento.gestordevagas.modules.company.entities.JobEntity;
import com.thiagonascimento.gestordevagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    public JobEntity execute(JobEntity job) {
        return this.jobRepository.save(job);
    }
}
