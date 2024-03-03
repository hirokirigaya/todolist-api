package com.danielferreira.todolist.controllers;

import com.danielferreira.todolist.dtos.todos.CreateTodoDTO;
import com.danielferreira.todolist.dtos.todos.TodoResponseDTO;
import com.danielferreira.todolist.entities.TodoEntity;
import com.danielferreira.todolist.useCases.TodoUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoUseCase todoUseCase;

    @GetMapping
    public ResponseEntity<List<TodoResponseDTO>> getAll() {
        return ResponseEntity.status(200).body(todoUseCase.getAllByUser());
    }

    @PostMapping("/create")
    public ResponseEntity createTodo(@RequestBody @Valid CreateTodoDTO todo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(422).body(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst());
        }
        return ResponseEntity.status(201).body(todoUseCase.create(todo));
    }




}
