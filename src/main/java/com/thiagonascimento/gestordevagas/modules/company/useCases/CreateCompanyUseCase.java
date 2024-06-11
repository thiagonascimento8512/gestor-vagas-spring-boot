package com.thiagonascimento.gestordevagas.modules.company.useCases;

import com.thiagonascimento.gestordevagas.exceptions.CompanyFoundException;
import com.thiagonascimento.gestordevagas.modules.company.repositories.CompanyRepository;
import com.thiagonascimento.gestordevagas.modules.company.entities.CompanyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity execute(CompanyEntity company) {
        this.companyRepository.findByUsernameOrEmail(company.getUsername(), company.getEmail())
                .ifPresent(c -> {
                    throw new CompanyFoundException();
                });

        return this.companyRepository.save(company);
    }

}
