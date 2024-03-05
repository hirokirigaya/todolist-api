package com.danielferreira.todolist.dtos.todos;

public record UpdateTodoDTO(
        String id,
        String title,
        String description
) {
}
