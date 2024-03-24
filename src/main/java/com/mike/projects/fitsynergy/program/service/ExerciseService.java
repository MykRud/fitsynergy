package com.mike.projects.fitsynergy.program.service;

import com.mike.projects.fitsynergy.program.dto.TimerRequestDto;
import com.mike.projects.fitsynergy.program.model.Exercise;
import com.mike.projects.fitsynergy.program.model.ExerciseContext;
import com.mike.projects.fitsynergy.program.repo.ExerciseContextRepo;
import com.mike.projects.fitsynergy.program.repo.ExerciseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepo exerciseRepo;
    private final ExerciseContextRepo exerciseContextRepo;

    public Optional<Exercise> getExerciseByName(String name) {
        return exerciseRepo.findByName(name);
    }

    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepo.save(exercise);
    }

    public ExerciseContext saveExerciseContext(ExerciseContext exerciseContext){
        return exerciseContextRepo.save(exerciseContext);
    }

    public ExerciseContext getExerciseContext(Integer exerciseContextId){
        return exerciseContextRepo.findById(exerciseContextId)
                .orElseThrow(() -> new RuntimeException("Cannot find exercise context with id " + exerciseContextId));
    }

    public ExerciseContext recordTime(Integer exerciseId, TimerRequestDto timerRequestDto) {
        ExerciseContext exerciseContext = getExerciseContext(exerciseId);
        exerciseContext.setExecutionTime((double) timerRequestDto.getExecutionTime() / 60.0);
        exerciseContext.setStartDate(new Date(timerRequestDto.getStartDate().getTime()));
        exerciseContext.setCompleted(true);
        return exerciseContextRepo.save(exerciseContext);
    }
}
