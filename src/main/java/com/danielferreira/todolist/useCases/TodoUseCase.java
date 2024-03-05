package com.danielferreira.todolist.useCases;

import com.danielferreira.todolist.dtos.todos.CreateTodoDTO;
import com.danielferreira.todolist.dtos.todos.TodoResponseDTO;
import com.danielferreira.todolist.dtos.todos.UpdateTodoDTO;
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

    public TodoResponseDTO findById(String id) {
        var todo = todoRepository.findById(id);
        return todo.map(todoEntity -> new TodoResponseDTO(
                todoEntity.getId(),
                todoEntity.getTitle(),
                todoEntity.getDescription(),
                todoEntity.isCompleted(),
                todoEntity.getCreatedAt(),
                todoEntity.getUpdatedAt())).orElse(null);
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

    public boolean delete(String id) {
        var todo = todoRepository.findById(id);
        if (todo.isPresent()) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public TodoResponseDTO update(UpdateTodoDTO todo) {
        var updateTodo = todoRepository.getReferenceById(todo.id());
        updateTodo.setTitle(todo.title());
        updateTodo.setDescription(todo.description());
        todoRepository.save(updateTodo);

        return new TodoResponseDTO(
                updateTodo.getId(),
                updateTodo.getTitle(),
                updateTodo.getDescription(),
                updateTodo.isCompleted(),
                updateTodo.getCreatedAt(),
                updateTodo.getUpdatedAt()
        );
    };

    public TodoResponseDTO toggleStatus(String id) {
        var updateTodo = todoRepository.getReferenceById(id);
        updateTodo.setCompleted(!updateTodo.isCompleted());
        todoRepository.save(updateTodo);

        return new TodoResponseDTO(
                updateTodo.getId(),
                updateTodo.getTitle(),
                updateTodo.getDescription(),
                updateTodo.isCompleted(),
                updateTodo.getCreatedAt(),
                updateTodo.getUpdatedAt()
        );
    };

}
