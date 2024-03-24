package com.mike.projects.fitsynergy.config;

import com.mike.projects.fitsynergy.users.exception.UserNotFoundException;
import com.mike.projects.fitsynergy.users.model.User;
import com.mike.projects.fitsynergy.users.repo.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SessionInfo {

    private final UserRepo userRepo;

    public SessionInfo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getCurrentUser(){
        User user = null;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(userRepo.findIfEmailPresent(username).isPresent()){
            user = userRepo.findByEmail(username)
                    .orElseThrow(() -> new UserNotFoundException("Cannot find username " + username));
        }
        return user;
    }
}
