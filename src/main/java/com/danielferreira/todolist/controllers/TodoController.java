package com.danielferreira.todolist.controllers;

import com.danielferreira.todolist.dtos.todos.CreateTodoDTO;
import com.danielferreira.todolist.dtos.todos.UpdateTodoDTO;
import com.danielferreira.todolist.response.ResponseHandler;
import com.danielferreira.todolist.useCases.TodoUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoUseCase todoUseCase;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return  ResponseHandler.generateResponse("Busca realizada com sucesso!", HttpStatus.OK,todoUseCase.getAllByUser());
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createTodo(@RequestBody @Valid CreateTodoDTO todo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseHandler.generateResponse(String.valueOf(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst()), HttpStatus.BAD_REQUEST);
        }
        return ResponseHandler.generateResponse("Usuário cadastrado com sucesso!", HttpStatus.CREATED, todoUseCase.create(todo));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteTodo(@PathVariable String id) {
        if (id == null) return ResponseHandler.generateResponse("Tarefa não encontrada.", HttpStatus.BAD_REQUEST);
        var deleted = todoUseCase.delete(id);
        if (!deleted) return ResponseHandler.generateResponse("Tarefa não encontrada.", HttpStatus.BAD_REQUEST);
        return ResponseHandler.generateResponse("Tarefa deletada com sucesso", HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateTodo(@PathVariable String id, @RequestBody @Valid CreateTodoDTO data, BindingResult bindingResult) {
        if (id == null) return ResponseHandler.generateResponse("Tarefa não encontrada.", HttpStatus.BAD_REQUEST);
        if (bindingResult.hasErrors()) {
            return ResponseHandler.generateResponse(String.valueOf(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst()), HttpStatus.BAD_REQUEST);
        }
        if (todoUseCase.findById(id) == null) return ResponseEntity.badRequest().body("Tarefa não encontrada.");
        return ResponseHandler.generateResponse("Tarefa atualizada com sucesso!", HttpStatus.OK,todoUseCase.update(new UpdateTodoDTO(id, data.title(), data.description())));
    }

    @PostMapping("/toggle-status/{id}")
    public ResponseEntity<Object> toggleStatusTodo(@PathVariable String id) {
        if (id == null) return ResponseHandler.generateResponse("Tarefa não encontrada.", HttpStatus.BAD_REQUEST);
        if (todoUseCase.findById(id) == null) return ResponseHandler.generateResponse("Tarefa não encontrada.", HttpStatus.BAD_REQUEST);
        return ResponseHandler.generateResponse("Status da tarefa atualizado com sucesso!", HttpStatus.OK, todoUseCase.toggleStatus(id));
    }
}
