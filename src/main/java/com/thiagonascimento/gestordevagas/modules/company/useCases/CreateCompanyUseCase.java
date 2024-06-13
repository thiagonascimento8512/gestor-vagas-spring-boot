package com.thiagonascimento.gestordevagas.modules.company.useCases;

import com.thiagonascimento.gestordevagas.exceptions.CompanyFoundException;
import com.thiagonascimento.gestordevagas.modules.company.repositories.CompanyRepository;
import com.thiagonascimento.gestordevagas.modules.company.entities.CompanyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity company) {
        this.companyRepository.findByUsernameOrEmail(company.getUsername(), company.getEmail())
                .ifPresent(c -> {
                    throw new CompanyFoundException();
                });

        var password = this.passwordEncoder.encode(company.getPassword());

        company.setPassword(password);

        return this.companyRepository.save(company);
    }

}
