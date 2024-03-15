package com.danielferreira.todolist.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank(message = "Usuário é obrigatório.")
        String username,
        @NotBlank(message = "O e-mail é obrigatório.")
        String email,
        @NotBlank(message = "A senha é obrigatória.")
        String password,

        @NotBlank(message = "A confirmação da senha é obrigatória.")
        String confirm_password
) {
}
