package com.danielferreira.todolist.useCases;

import com.danielferreira.todolist.entities.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserUseCase {

    public UserEntity getUser() {
        var userDetails = SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return (UserEntity) userDetails;
    }
}
