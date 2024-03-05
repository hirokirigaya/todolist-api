package com.danielferreira.todolist.useCases;

import com.danielferreira.todolist.entities.UserEntity;
import com.danielferreira.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserUseCase {

    @Autowired
    private UserRepository userRepository;
    public UserEntity getUser() {
        var userDetails = SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return (UserEntity) userDetails;
    }

    public UserEntity findUserByUsername(String username) {
        return (UserEntity) userRepository.findByUsername(username);
    }
}
