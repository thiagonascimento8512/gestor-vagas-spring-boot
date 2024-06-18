package com.thiagonascimento.gestordevagas.modules.company.useCases;

import com.thiagonascimento.gestordevagas.exceptions.CompanyNotFoundException;
import com.thiagonascimento.gestordevagas.modules.company.entities.JobEntity;
import com.thiagonascimento.gestordevagas.modules.company.repositories.CompanyRepository;
import com.thiagonascimento.gestordevagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public JobEntity execute(JobEntity job) {
        companyRepository.findById(job.getCompanyId())
                .orElseThrow(CompanyNotFoundException::new);
        return this.jobRepository.save(job);
    }
}
