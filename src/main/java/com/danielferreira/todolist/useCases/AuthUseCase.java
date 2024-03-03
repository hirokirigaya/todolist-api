package com.danielferreira.todolist.useCases;

import com.danielferreira.todolist.dtos.auth.AuthenticationDTO;
import com.danielferreira.todolist.entities.UserEntity;
import com.danielferreira.todolist.infra.security.TokenService;
import com.danielferreira.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUseCase {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    public String login(AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        return tokenService.generateToken((UserEntity) auth.getPrincipal());
    }

    public boolean register(AuthenticationDTO registerRequestDTO) {
        if (this.userRepository.findByUsername(registerRequestDTO.username()) != null) return false;
        var encryptedPassword = new BCryptPasswordEncoder().encode(registerRequestDTO.password());
        var user = new UserEntity(registerRequestDTO.username(),encryptedPassword);
        userRepository.save(user);
        return true;
    }

}
