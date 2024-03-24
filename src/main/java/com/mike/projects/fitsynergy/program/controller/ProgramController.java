package com.mike.projects.fitsynergy.program.controller;

import com.mike.projects.fitsynergy.program.model.Program;
import com.mike.projects.fitsynergy.program.service.ProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Tag(
        name = "APIs for general program processings of FitSynergy",
        description = "APIs for program to CREATE, UPDATE, ENRICH, FETCH AND DELETE program (for trainer and admin)"
)
@Controller
@RequestMapping("/api/programs")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @Operation(
            summary = "Get program API",
            description = "API retrieves comprehensive information on all available fitness programs within our system, " +
                    "facilitating informed decisions for users considering further subscription. " +
                    "This functionality provides detailed insights into program features, duration, " +
                    "and associated benefits, empowering users to make well-informed choices based on their fitness preferences and goals."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error"
            )
    })
    @GetMapping("/{programId}")
    public ModelAndView getProgram(@PathVariable("programId") Integer programId){
        ModelAndView mv = new ModelAndView("main-ui/program-view");

        Program program = programService.getProgramById(programId);
        mv.addObject("program", program);

        mv.addObject("imageId", (int) (Math.random() * 5 + 1));

        return mv;
    }

    @GetMapping
    public ModelAndView getAllPrograms(){
        ModelAndView mv = new ModelAndView("main-ui/general-program-view");

        List<Program> programs = programService.getIncomingProgramsInLimitNotTaken(15);
        mv.addObject("programs", programs);

        return mv;
    }

    @Operation(
            summary = "Get all program exercises API",
            description = "API retrieves a detailed dataset encompassing all " +
                    "exercise-related information within a specific fitness program available in our system. " +
                    "This feature equips users with a comprehensive overview of exercises, including descriptions, " +
                    "difficulty levels, and recommended durations, aiding them in making informed decisions when selecting a " +
                    "program based on their fitness objectives and preferences."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error"
            )
    })
    @GetMapping("/{programId}/exercises")
    public ModelAndView getProgramExercises(@PathVariable("programId") Integer programId){
        ModelAndView mv = new ModelAndView("main-ui/program-exercises-list");

        Program program = programService.getProgramById(programId);
        mv.addObject("programId", programId);

        mv.addObject("exercises", program.getExercises());

        return mv;
    }
}
