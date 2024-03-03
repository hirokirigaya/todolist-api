package com.danielferreira.todolist.repositories;

import com.danielferreira.todolist.entities.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity, String> {

    List<TodoEntity> findByUserId(String userID);
}
