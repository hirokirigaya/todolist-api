package com.danielferreira.todolist.controllers;

import com.danielferreira.todolist.dtos.auth.AuthenticationDTO;
import com.danielferreira.todolist.dtos.auth.LoginDTO;
import com.danielferreira.todolist.dtos.user.UserResponseDTO;
import com.danielferreira.todolist.response.ResponseHandler;
import com.danielferreira.todolist.response.UserResponse;
import com.danielferreira.todolist.useCases.AuthUseCase;
import com.danielferreira.todolist.useCases.UserUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthUseCase authUseCase;

    @Autowired
    UserUseCase userUseCase;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDTO data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseHandler.generateResponse(String.valueOf(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().get()), HttpStatus.BAD_REQUEST);
        }
        var user = userUseCase.findUserByEmail(data.email());
        if (user == null) return ResponseHandler.generateResponse("E-mail é/ou senha incorreto(s).", HttpStatus.UNPROCESSABLE_ENTITY);
        var token = authUseCase.login(data);
        var userObj = UserResponse.generateResponse(token, new UserResponseDTO(user.getId(), user.getUsername(), user.getAvatar(), user.getEmail()));
        return ResponseHandler.generateResponse("Login realizado com sucesso.", HttpStatus.OK, userObj);

    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid AuthenticationDTO data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseHandler.generateResponse(String.valueOf(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().get()), HttpStatus.BAD_REQUEST);
        }
        boolean notHasAccount = authUseCase.register(data);
        if (!notHasAccount) return ResponseHandler.generateResponse("Usuário já cadastrado!", HttpStatus.BAD_REQUEST);
        return ResponseHandler.generateResponse("Usuário registrado com sucesso!", HttpStatus.CREATED);
    }
}
