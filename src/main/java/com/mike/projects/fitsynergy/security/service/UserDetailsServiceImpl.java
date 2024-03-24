package com.mike.projects.fitsynergy.security.service;

import com.mike.projects.fitsynergy.users.model.User;
import com.mike.projects.fitsynergy.users.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(userRepo.findIfEmailPresent(username).isPresent()) {
            User userFromDb = userRepo.findByEmail(username).get();
            return new UserCredential(userFromDb);
        }
        throw new UsernameNotFoundException(String.format("Cannot find user with email %s", username));
    }
}
