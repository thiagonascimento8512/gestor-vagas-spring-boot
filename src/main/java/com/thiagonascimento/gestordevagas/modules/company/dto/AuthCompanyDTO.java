package com.thiagonascimento.gestordevagas.modules.company.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCompanyDTO {

    @NotNull(message = "O campo senha é obrigatório")
    private String password;

    @NotNull(message = "O campo usuário é obrigatório")
    private String username;
}
