package com.mike.projects.fitsynergy.program.integrational;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mike.projects.fitsynergy.program.controller.ConstructorController;
import com.mike.projects.fitsynergy.program.dto.ExerciseRequestDto;
import com.mike.projects.fitsynergy.program.dto.ProgramRequestDto;
import com.mike.projects.fitsynergy.program.model.*;
import com.mike.projects.fitsynergy.program.service.ConstructorService;
import com.mike.projects.fitsynergy.program.service.OccupationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "/application-test.properties")
public class ConstructorControllerTest {

    private static MockHttpServletRequest request;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OccupationService occupationService;
    @MockBean
    private ConstructorService constructorService;

    @Test
    @WithMockUser(username = "adam@gmail.com", password = "password", roles = {"TRAINER"})
    public void initProgramConstructView_shouldSuccess() throws Exception{

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/construct"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mv = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mv, "main-ui/program-constructor-init");

    }

    @Test
    @WithMockUser(username = "adam@gmail.com", password = "password", roles = {"TRAINER"})
    public void initProgramConstruct_shouldSuccess() throws Exception{

        ProgramRequestDto programRequestDto = new ProgramRequestDto();
        programRequestDto.setProgramName("Push Ups Program");
        programRequestDto.setOccupationId("1");
        programRequestDto.setLevel("FIRST_LEVEL");

        Program program = new Program();
        program.setId(1);
        program.setName(programRequestDto.getProgramName());
        program.setLevel(Level.valueOf(programRequestDto.getLevel()));
        Occupation occupation = new Occupation();
        occupation.setId(1);
        occupation.setName("Hard Fit");
        program.setOccupation(occupation);

        when(constructorService.createProgram(any())).thenReturn(program);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/construct")
                .flashAttr("programRequest", programRequestDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/construct/enrich/1"));

    }

    @Test
    public void enrichProgramConstructView_shouldSuccess() throws Exception{

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/construct/enrich/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("adam@gmail.com").password("password").roles("TRAINER")))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mv = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mv, "main-ui/program-constructor");
        ModelAndViewAssert.assertModelAttributeValue(mv, "programId", 1);
    }

    @Test
    public void enrichProgramConstruct_shouldSuccess() throws Exception{

        // Expected
        List<ExerciseRequestDto> exercises = new ArrayList<>();

        String jsonRequest = "[{\"name\":\"Pull Ups\",\"sets\":10,\"reps\":10,\"time\":10,\"date\":1709762400000,\"videoLink\":null}]";

        ExerciseRequestDto exerciseRequestDto = new ExerciseRequestDto();
        exerciseRequestDto.setSets(10);
        exerciseRequestDto.setReps(10);
        exerciseRequestDto.setDate(Date.valueOf(LocalDate.now()));
        exerciseRequestDto.setName("Pull Ups");
        exerciseRequestDto.setTime((int) 10.0);

        exercises.add(exerciseRequestDto);

        // Mock data
        List<ExerciseContext> mockExercises = new ArrayList<>();

        ExerciseContext mockExerciseContext = new ExerciseContext();
        mockExerciseContext.setStartDate(Date.valueOf(LocalDate.now()));
        mockExerciseContext.setCompleted(false);
        mockExerciseContext.setExecutionTime(10.0);
        mockExerciseContext.setSets(10);
        mockExerciseContext.setReps(10);
        Exercise mockExercise = new Exercise();
        mockExercise.setName("Pull Ups");
        mockExerciseContext.setExercise(mockExercise);

        mockExercises.add(mockExerciseContext);

        Occupation mockOccupation = new Occupation();
        mockOccupation.setName("Hard Fit");
        mockOccupation.setId(1);

        Program mockProgram = new Program();
        mockProgram.setId(1);
        mockProgram.setName("Push Ups Program");
        mockProgram.setLevel(Level.valueOf("FIRST_LEVEL"));
        mockProgram.setOccupation(mockOccupation);
        mockProgram.setExercises(mockExercises);

        System.out.println(new ObjectMapper().writeValueAsString(exerciseRequestDto));

        when(constructorService.enrichProgramWithExercises(1, exercises)).thenReturn(mockProgram);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/construct/enrich/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("adam@gmail.com").password("password").roles("TRAINER"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest))
                .andExpect(status().isOk());

    }


}
