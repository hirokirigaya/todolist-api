package com.danielferreira.todolist.dtos.todos;

import jakarta.validation.constraints.NotNull;

public record CreateTodoDTO(
        @NotNull(message = "O título é obrigatório.")
        String title,
        String description
) {
}
