package com.mike.projects.fitsynergy.users.service;

import com.mike.projects.fitsynergy.security.model.SystemUserDetails;
import com.mike.projects.fitsynergy.users.dto.UserRequestDto;
import com.mike.projects.fitsynergy.users.dto.UserResponse;
import com.mike.projects.fitsynergy.users.exception.UserAlreadyExistsException;
import com.mike.projects.fitsynergy.users.exception.UserNotFoundException;
import com.mike.projects.fitsynergy.users.mapper.UserMapper;
import com.mike.projects.fitsynergy.users.model.User;
import com.mike.projects.fitsynergy.users.repo.ClientRepo;
import com.mike.projects.fitsynergy.users.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final ClientRepo clientRepo;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserRequestDto requestDto) {
        User userToRegister = new User();

        // Check is user with such email is already exists
        if(userRepo.findIfEmailPresent(requestDto.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("User with email " + requestDto.getEmail() + " is already exists");
        }

        UserMapper.mapUserRequestDto(requestDto, userToRegister);

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        userToRegister.setPassword(encodedPassword);

        userToRegister.setUserDetails(SystemUserDetails.getInstanceOfClient());

        return saveUser(userToRegister);
    }

    private User saveUser(User user){
        User savedUser = userRepo.save(user);
        clientRepo.addClient(user.getId());
        return savedUser;
    }

    public User getUser(Integer userId){
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Cannot found user with id " + userId));
    }

    public void addTrainerRole(Integer userId) {
        userRepo.addTrainerRole(userId);
    }

    public List<UserResponse> getUsers(Integer limit) {
        return userRepo.findAll(PageRequest.of(0, limit).withSort(Sort.Direction.DESC, "id"))
                .stream()
                .map(u -> {
                    return UserResponse.builder()
                            .id(u.getId())
                            .firstName(u.getFirstName())
                            .lastName(u.getLastName())
                            .age(u.getAge())
                            .email(u.getEmail())
                            .role(u.getUserDetails().getRole().name())
                            .gender(u.getGender())
                            .build();
                })
                .toList();
    }

    public void removeTrainerRole(Integer userId) {
        userRepo.removeTrainerRole(userId);
    }
}
