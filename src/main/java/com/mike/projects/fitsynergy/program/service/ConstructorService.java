package com.mike.projects.fitsynergy.program.service;

import com.mike.projects.fitsynergy.program.dto.ExerciseRequestDto;
import com.mike.projects.fitsynergy.program.dto.ProgramRequestDto;
import com.mike.projects.fitsynergy.program.model.Exercise;
import com.mike.projects.fitsynergy.program.model.ExerciseContext;
import com.mike.projects.fitsynergy.program.model.Level;
import com.mike.projects.fitsynergy.program.model.Program;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConstructorService {

    private final ProgramService programService;
    private final OccupationService occupationService;
    private final ExerciseService exerciseService;

    public Program createProgram(ProgramRequestDto programRequestDto){
        Program program = new Program();
        program.setName(programRequestDto.getProgramName());
        program.setLevel(Level.valueOf(programRequestDto.getLevel()));
        if(programRequestDto.getOccupationId() != null)
            program.setOccupation(occupationService.getOccupationById(Integer.parseInt(programRequestDto.getOccupationId())));

        return programService.saveProgram(program);
    }

    public Program enrichProgramWithExercises(Integer programId, List<ExerciseRequestDto> exercisesDto) {
        Program programFormDb = programService.getProgramById(programId);

        List<ExerciseContext> exerciseContexts = new ArrayList<>();
        for(ExerciseRequestDto exerciseRequest : exercisesDto){
            Exercise exercise = getExerciseEntity(exerciseRequest);
            ExerciseContext ec = enrichExerciseWithContext(exerciseRequest, exercise);
            exerciseContexts.add(ec);
        }

        programFormDb.setExercises(exerciseContexts);
        return programService.saveProgram(programFormDb);
    }

    private ExerciseContext enrichExerciseWithContext(ExerciseRequestDto exerciseRequest, Exercise exercise) {
        ExerciseContext ec = new ExerciseContext();
        ec.setExercise(exercise);
        ec.setSets(exerciseRequest.getSets());
        ec.setReps(exerciseRequest.getReps());
        ec.setExecutionTime(exerciseRequest.getTime());
        ec.setStartDate(new Date(exerciseRequest.getDate().getTime()));
        ec.setCompleted(false);
        return exerciseService.saveExerciseContext(ec);
    }

    private Exercise getExerciseEntity(ExerciseRequestDto exerciseRequest) {
        Optional<Exercise> exerciseOpt = exerciseService.getExerciseByName(exerciseRequest.getName());
        if(exerciseOpt.isPresent()){
            return exerciseOpt.get();
        } else {
            Exercise exercise = new Exercise();
            exercise.setName(exerciseRequest.getName());
            exercise.setVideoLink(exerciseRequest.getVideoLink());
            return exerciseService.saveExercise(exercise);
        }
    }
}
