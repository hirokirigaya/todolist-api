package com.danielferreira.todolist.controllers;

import com.danielferreira.todolist.dtos.auth.AuthenticationDTO;
import com.danielferreira.todolist.dtos.auth.LoginResponseDTO;
import com.danielferreira.todolist.useCases.AuthUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthUseCase authUseCase;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(422).body(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst());
        }
        var token = authUseCase.login(data);
        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid AuthenticationDTO data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(422).body(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst());
        }
        boolean notHasAccount = authUseCase.register(data);
        if (!notHasAccount) return ResponseEntity.status(400).body("Usuário já registrado.");
        return ResponseEntity.status(201).body("Usuário registrado com sucesso!");
    }
}
