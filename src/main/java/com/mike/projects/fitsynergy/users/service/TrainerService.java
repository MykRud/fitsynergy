package com.mike.projects.fitsynergy.users.service;

import com.mike.projects.fitsynergy.users.exception.UserNotFoundException;
import com.mike.projects.fitsynergy.users.mapper.TrainerMapper;
import com.mike.projects.fitsynergy.users.model.Trainer;
import com.mike.projects.fitsynergy.users.model.User;
import com.mike.projects.fitsynergy.users.repo.ClientRepo;
import com.mike.projects.fitsynergy.users.repo.TrainerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final UserService userService;
    private final TrainerRepo trainerRepo;
    private final ClientRepo clientRepo;

    public List<Trainer> getAllTrainers(){
        return trainerRepo.findAll();
    }

    public Trainer getTrainerById(Integer id){
        return trainerRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Cannot find trainer with id " + id));
    }

    public Trainer saveTrainer(Trainer trainer){
        return trainerRepo.save(trainer);
    }

    public Trainer updateTrainerData(Integer id, Trainer trainer){
        Trainer trainerFromDb = trainerRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Cannot find trainer with id " + id));
        TrainerMapper.mapTrainer(trainer, trainerFromDb);
        return trainerRepo.save(trainerFromDb);
    }

    public void deleteTrainer(Integer id){
        Trainer trainerFromDb = trainerRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Cannot find trainer with id " + id));
        trainerRepo.delete(trainerFromDb);
    }

    public void setTrainer(Integer userId){

        User user = userService.getUser(userId);
        trainerRepo.addTrainer(user.getId());
        clientRepo.deleteClient(user.getId());
        userService.addTrainerRole(user.getUserDetails().getId());
    }

    public void unsetTrainer(Integer userId){

        User user = userService.getUser(userId);
        trainerRepo.deleteTrainer(user.getId());
        clientRepo.addClient(user.getId());
        userService.removeTrainerRole(user.getUserDetails().getId());
    }
}
