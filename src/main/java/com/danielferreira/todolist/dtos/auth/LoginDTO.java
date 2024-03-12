package com.danielferreira.todolist.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "O e-mail é obrigatório.")
        String email,
        @NotBlank(message = "A senha é obrigatória.")
        String password
) {
}
