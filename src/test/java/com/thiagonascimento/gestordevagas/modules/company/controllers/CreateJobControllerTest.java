package com.thiagonascimento.gestordevagas.modules.company.controllers;

import com.thiagonascimento.gestordevagas.modules.company.dto.CreateJobDTO;
import com.thiagonascimento.gestordevagas.modules.company.entities.CompanyEntity;
import com.thiagonascimento.gestordevagas.modules.company.repositories.CompanyRepository;
import com.thiagonascimento.gestordevagas.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static com.thiagonascimento.gestordevagas.utils.TestUtils.objectToJson;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should be able to create a job")
    public void shouldBeAbleToCreateAJob() throws Exception {

        var company = CompanyEntity.builder()
                .name("Empresa Teste")
                .email("empresa@gmail.com")
                .password("123456")
                .username("empresateste")
                .description("Empresa de Tecnologia")
                .build();

        var createdCompany = companyRepository.saveAndFlush(company);

        var jobDTO = CreateJobDTO.builder()
                .description("Desenvolver aplicações Java")
                .benefits("VR, VT, Plano de Saúde")
                .level("Júnior")
                .build();


        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(jobDTO))
                        .header("Authorization", "Bearer " + TestUtils.generateToken(createdCompany.getId(), "gestordevagas"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should not be able to create a job if the company does not exist")
    public void shouldNotBeAbleToCreateAJobIfTheCompanyDoesNotExist() throws Exception {

        var jobDTO = CreateJobDTO.builder()
                .description("Desenvolver aplicações Java")
                .benefits("VR, VT, Plano de Saúde")
                .level("Júnior")
                .build();


        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(jobDTO))
                        .header("Authorization", "Bearer " + TestUtils.generateToken(UUID.randomUUID(), "gestordevagas"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
