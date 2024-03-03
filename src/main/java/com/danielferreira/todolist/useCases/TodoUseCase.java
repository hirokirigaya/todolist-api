package com.danielferreira.todolist.useCases;

import com.danielferreira.todolist.dtos.todos.CreateTodoDTO;
import com.danielferreira.todolist.dtos.todos.TodoResponseDTO;
import com.danielferreira.todolist.entities.TodoEntity;
import com.danielferreira.todolist.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoUseCase {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserUseCase userUseCase;




    public List<TodoResponseDTO> getAllByUser() {
        var userId = userUseCase.getUser().getId();
        return todoRepository.findByUserId(userId)
                .stream()
                .map(todo -> new TodoResponseDTO(
                        todo.getId(),
                        todo.getTitle(),
                        todo.getDescription(),
                        todo.isCompleted(),
                        todo.getCreatedAt(),
                        todo.getUpdatedAt()))
                .toList();
    }

    public TodoResponseDTO create(CreateTodoDTO todo) {
        var userId = userUseCase.getUser().getId();
        TodoEntity newTodo = new TodoEntity(todo.title(), todo.description(), userId, false);
        TodoEntity createdTodo =  todoRepository.save(newTodo);
        return new TodoResponseDTO(
                createdTodo.getId(),
                createdTodo.getTitle(),
                createdTodo.getDescription(),
                createdTodo.isCompleted(),
                createdTodo.getCreatedAt(),
                createdTodo.getUpdatedAt()
        );
    }
}
