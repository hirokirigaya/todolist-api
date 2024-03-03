package com.danielferreira.todolist.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank(message = "O username é obrigatório.")
        String username,
        @NotBlank(message = "A senha é obrigatória.")
        String password
) {
}
