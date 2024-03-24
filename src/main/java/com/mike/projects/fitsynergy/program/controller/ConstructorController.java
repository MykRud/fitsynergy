package com.mike.projects.fitsynergy.program.controller;

import com.mike.projects.fitsynergy.program.dto.ErrorResponseDto;
import com.mike.projects.fitsynergy.program.dto.ProgramRequestDto;
import com.mike.projects.fitsynergy.program.dto.ExerciseRequestDto;
import com.mike.projects.fitsynergy.program.model.Program;
import com.mike.projects.fitsynergy.program.service.ConstructorService;
import com.mike.projects.fitsynergy.program.service.OccupationService;
import com.mike.projects.fitsynergy.program.service.ProgramService;
import com.mike.projects.fitsynergy.users.utils.ErrorValidatorUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Tag(
        name = "APIs for Constructor of FitSynergy",
        description = "APIs for Constructor to CREATE, UPDATE, ENRICH, FETCH AND DELETE program (for trainer and admin)"
)
@Controller
@RequestMapping("/api/construct")
@RequiredArgsConstructor
public class ConstructorController {

    private final OccupationService occupationService;
    private final ConstructorService constructorService;

    @Operation(
            summary = "Get initial constructor view API",
            description = "API to get initial constructor view to create initiate processing for creating program inside FitSynergy system"
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
    @GetMapping
    public ModelAndView getConstructorInitView(){
        ModelAndView mv = new ModelAndView("main-ui/program-constructor-init");

        mv.addObject("occupations", occupationService.getAllOccupations());
        mv.addObject("constructorRequest", new ProgramRequestDto());

        return mv;
    }

    @Operation(
            summary = "Create Program API",
            description = "API to create new program for client inside FitSynergy system. This program doesn't contain any exercises, only initalized data"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error"
            )
    })
    @PostMapping
    public ModelAndView constructorInitLogic(@Valid @ModelAttribute ProgramRequestDto requestDto,
                                             BindingResult bindingResult){
        ModelAndView mv = new ModelAndView();

        // Validation
        Map<String, String> errors = ErrorValidatorUtil.validateDto(bindingResult);
        if(!errors.isEmpty()){
            mv.addObject("errors", errors);
            return mv;
        }

        Program program = constructorService.createProgram(requestDto);

        mv.setViewName("redirect:/api/construct/enrich/" + program.getId());

        return mv;
    }

    @Operation(
            summary = "Get enrich view of constructor API",
            description = "API to get enrich constructor view to enrich program with desired exercises inside FitSynergy system"
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
    @GetMapping("/enrich/{programId}")
    public ModelAndView getConstructorView(@PathVariable("programId") Integer programId){
        ModelAndView mv = new ModelAndView("main-ui/program-constructor");

        mv.addObject("programId", programId);

        return mv;
    }

    @Operation(
            summary = "Enrich Program API",
            description = "API to enrich program with exercises based on programId"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @ResponseBody
    @PostMapping(value = "/enrich/{programId}")
    public String enrichProgramWithExercises(@PathVariable("programId") Integer programId,
                                             @RequestBody List<ExerciseRequestDto> exercises){

        Program program = constructorService.enrichProgramWithExercises(programId, exercises);

        return "/api/programs/" + program.getId();
    }

}
