package com.mike.projects.fitsynergy.program.service;

import com.google.common.collect.Lists;
import com.mike.projects.fitsynergy.FitsynergyApplication;
import com.mike.projects.fitsynergy.program.dto.ExerciseRequestDto;
import com.mike.projects.fitsynergy.program.dto.ProgramRequestDto;
import com.mike.projects.fitsynergy.program.model.*;
import com.mike.projects.fitsynergy.program.repo.ExerciseContextRepo;
import com.mike.projects.fitsynergy.program.repo.ExerciseRepo;
import com.mike.projects.fitsynergy.program.repo.OccupationRepo;
import com.mike.projects.fitsynergy.program.repo.ProgramRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
class ConstructorServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ConstructorService constructorService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private OccupationService occupationService;
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private ProgramRepo programRepo;
    @Autowired
    private OccupationRepo occupationRepo;
    @Autowired
    private ExerciseRepo exerciseRepo;
    @Autowired
    private ExerciseContextRepo exerciseContextRepo;

    // SQL Scripts
    @Value("${sql.script.create.occupation}")
    private String createOccupationScript;
    @Value("${sql.script.create.program}")
    private String createProgramScript;

    @Value("${sql.script.delete.client}")
    private String deleteClientScript;
    @Value("${sql.script.delete.trainer}")
    private String deleteTrainerScript;
    @Value("${sql.script.delete.user}")
    private String deleteUserScript;
    @Value("${sql.script.delete.user.details}")
    private String deleteUserDetailsScript;
    @Value("${sql.script.delete.occupation}")
    private String deleteOccupationScript;
    @Value("${sql.script.delete.program.exercise}")
    private String deleteProgramExerciseScript;
    @Value("${sql.script.delete.program}")
    private String deleteProgramScript;
    @Value("${sql.script.delete.program.client}")
    private String deleteProgramClientScript;
    @Value("${sql.script.delete.exercise}")
    private String deleteExerciseScript;
    @Value("${sql.script.delete.exercise.context}")
    private String deleteExerciseContextScript;

    @Value("${sql.script.reload.user.sequence}")
    private String reloadUserSequenceScript;
    @Value("${sql.script.reload.user.details.sequence}")
    private String reloadUserDetailsSequenceScript;
    @Value("${sql.script.reload.occupation.sequence}")
    private String reloadOccupationSequenceScript;
    @Value("${sql.script.reload.program.sequence}")
    private String reloadProgramSequenceScript;
    @Value("${sql.script.reload.program.client.sequence}")
    private String reloadProgramClientSequenceScript;
    @Value("${sql.script.reload.exercise.sequence}")
    private String reloadExerciseSequenceScript;
    @Value("${sql.script.reload.exercise.context.sequence}")
    private String reloadExerciseContextSequenceScript;

    @Test
    public void initProgramConstruct_Success(){

        // Вхідні дані
        ProgramRequestDto programRequestDto = new ProgramRequestDto();
        programRequestDto.setProgramName("Push Ups Program");
        programRequestDto.setLevel("FIRST_LEVEL");
        programRequestDto.setOccupationId("1");

        // Підготовка
        jdbcTemplate.update(createOccupationScript);

        // Очікувані результати
        Occupation mockOccupation = new Occupation();
        mockOccupation.setName("Hard Fit");
        mockOccupation.setId(1);

        Program expectedProgram = new Program();
        expectedProgram.setId(1);
        expectedProgram.setName(programRequestDto.getProgramName());
        expectedProgram.setLevel(Level.valueOf(programRequestDto.getLevel()));
        expectedProgram.setOccupation(mockOccupation);

        // Тестування
        Program actualProgram = constructorService.createProgram(programRequestDto);
        assertEquals(expectedProgram.getId(), actualProgram.getId());
        assertEquals(expectedProgram.getName(), actualProgram.getName());
        assertEquals(expectedProgram.getOccupation().getName(), actualProgram.getOccupation().getName());
        assertNull(actualProgram.getExercises());
        assertEquals(expectedProgram.getLevel().name(), actualProgram.getLevel().name());

    }

    @Test
    public void initProgramConstruct_ErrorOccurs_noOccupation(){

        // Вхідні дані
        ProgramRequestDto programRequestDto = new ProgramRequestDto();
        programRequestDto.setProgramName("Push Ups Program");
        programRequestDto.setLevel("FIRST_LEVEL");
        programRequestDto.setOccupationId("2");

        // Тестування
        assertThrows(RuntimeException.class, () -> constructorService.createProgram(programRequestDto));

    }

    @Test
    public void enrichProgramWithConstruct_Success(){

        // Вхідні дані
        ExerciseContext exerciseContext = new ExerciseContext();
        exerciseContext.setStartDate(Date.valueOf(LocalDate.now()));
        exerciseContext.setCompleted(false);
        exerciseContext.setExecutionTime(10.0);
        exerciseContext.setSets(10);
        exerciseContext.setReps(10);
        Exercise exercise = new Exercise();
        exercise.setName("Pull Ups");
        exerciseContext.setExercise(exercise);

        // Підготовка
        jdbcTemplate.update(createOccupationScript);
        jdbcTemplate.update(createProgramScript);

        ExerciseRequestDto exerciseRequestDto = new ExerciseRequestDto();
        exerciseRequestDto.setSets(exerciseContext.getSets());
        exerciseRequestDto.setReps(exerciseContext.getReps());
        exerciseRequestDto.setDate(exerciseContext.getStartDate());
        exerciseRequestDto.setName(exerciseContext.getExercise().getName());
        exerciseRequestDto.setVideoLink(exerciseContext.getExercise().getVideoLink());
        exerciseRequestDto.setTime((int) exerciseContext.getExecutionTime());

        // Тестування
        Program actualProgram = constructorService.enrichProgramWithExercises(1, List.of(exerciseRequestDto));

        assertEquals(1, actualProgram.getExercises().size());
        assertEquals(exerciseRequestDto.getName(), actualProgram.getExercises().get(0).getExercise().getName());
        assertEquals(exerciseRequestDto.getReps(), actualProgram.getExercises().get(0).getReps());
        assertEquals(exerciseRequestDto.getSets(), actualProgram.getExercises().get(0).getSets());
        assertEquals(exerciseRequestDto.getTime(), (int) actualProgram.getExercises().get(0).getExecutionTime());
        assertEquals(exerciseRequestDto.getDate(), actualProgram.getExercises().get(0).getStartDate());
        assertEquals(exerciseRequestDto.getVideoLink(), actualProgram.getExercises().get(0).getExercise().getVideoLink());

    }

    @AfterEach
    public void setupAfterTransaction(){
        jdbcTemplate.update(deleteClientScript);
        jdbcTemplate.update(deleteTrainerScript);
        jdbcTemplate.update(deleteProgramClientScript);
        jdbcTemplate.update(deleteUserScript);
        jdbcTemplate.update(deleteUserDetailsScript);
        jdbcTemplate.update(deleteProgramExerciseScript);
        jdbcTemplate.update(deleteProgramScript);
        jdbcTemplate.update(deleteOccupationScript);
        jdbcTemplate.update(deleteExerciseContextScript);
        jdbcTemplate.update(deleteExerciseScript);

        jdbcTemplate.update(reloadExerciseContextSequenceScript);
        jdbcTemplate.update(reloadExerciseSequenceScript);
        jdbcTemplate.update(reloadOccupationSequenceScript);
        jdbcTemplate.update(reloadProgramClientSequenceScript);
        jdbcTemplate.update(reloadProgramSequenceScript);
        jdbcTemplate.update(reloadUserDetailsSequenceScript);
        jdbcTemplate.update(reloadUserSequenceScript);
    }

}