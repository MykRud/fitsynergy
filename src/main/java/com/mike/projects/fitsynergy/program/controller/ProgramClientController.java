package com.mike.projects.fitsynergy.program.controller;

import com.mike.projects.fitsynergy.config.SessionInfo;
import com.mike.projects.fitsynergy.program.dto.ActivityDashboardResponseDto;
import com.mike.projects.fitsynergy.program.dto.DaysTimeDashboardDao;
import com.mike.projects.fitsynergy.program.dto.ProgramResponseDto;
import com.mike.projects.fitsynergy.program.dto.TimerRequestDto;
import com.mike.projects.fitsynergy.program.model.ExerciseContext;
import com.mike.projects.fitsynergy.program.model.ProgramClient;
import com.mike.projects.fitsynergy.program.service.ExerciseService;
import com.mike.projects.fitsynergy.program.service.ProgramClientService;
import com.mike.projects.fitsynergy.users.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Tag(
        name = "APIs for working with clients' programs of FitSynergy",
        description = "APIs for working with CREATE, UPDATE, ENRICH, FETCH AND DELETE operations on program (for client, trainer, admin)"
)
@Controller
@RequestMapping("/api/program-client")
@RequiredArgsConstructor
public class ProgramClientController {

    private final SessionInfo sessionInfo;
    private final ProgramClientService programClientService;
    private final ExerciseService exerciseService;

    @Operation(
            summary = "Get all programs view API",
            description = "API to get view of all programs based on client id"
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
    public ModelAndView getClientPrograms(){
        ModelAndView mv = new ModelAndView("main-ui/program-list");

        User activeUser = sessionInfo.getCurrentUser();

        List<ProgramResponseDto> allPrograms = programClientService.getAllClientPrograms(activeUser.getId());

        mv.addObject("programs", allPrograms);

        return mv;
    }

    @Operation(
            summary = "Get program view API",
            description = "API to get view of program with all its progressings and processings based on program id. " +
                    "Here we have lots of analytics data as execution time per weak(s), executed activities per month(s), " +
                    "days statistics as long as incoming exercises, completed exercises, etc"
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
    @GetMapping("/{programClientId}")
    public ModelAndView getClientProgram(@PathVariable("programClientId") Integer programClientId){

        ModelAndView mv = new ModelAndView("main-ui/program-dashboard");

        ProgramClient programClient = programClientService.getClientProgram(programClientId);

        mv.addObject("program", programClient);

        // Weeks and months activity stats
        ActivityDashboardResponseDto stats = programClientService.getStatisticsOfProgram(programClientId);

        // Weeks activity time
        mv.addObject("executionTime_pastWeak", stats.getExecutionTimePastWeak());
        mv.addObject("executionTime_currentWeak", stats.getExecutionTimeCurrentWeak());
        mv.addObject("executionDifferencePercentage", stats.getExecutionTimeDifferencePercentage());

        // Months activity numbers
        mv.addObject("executedActivities_pastMonth", stats.getExecutedActivitiesPastMonth());
        mv.addObject("executedActivities_currentMonth", stats.getExecutedActivitiesCurrentMonth());
        mv.addObject("executedActivities_differencePercentage", stats.getExecutedActivitiesDifferencePercentage());

        DaysTimeDashboardDao daysStatisticsExecutionOfActivities = programClientService.getDaysStatisticsExecutionOfActivitiesByProgramId(programClientId);

        mv.addObject("daysStats_days", daysStatisticsExecutionOfActivities.getDays());
        mv.addObject("daysStats_time", daysStatisticsExecutionOfActivities.getTime());

        List<ExerciseContext> incomingExercises = programClientService.getIncomingExercisesOfProgram(programClientId, 10);
        mv.addObject("incomingExercises", incomingExercises);

        List<ExerciseContext> completedExercises = programClientService.getCompletedExercisesCompletedSortByDate(programClientId, 5);
        mv.addObject("completedExercises", completedExercises);

        return mv;

    }

    @Operation(
            summary = "Subscribe on program API",
            description = "The subscription method in our fitness system handles the user enrollment process into fitness programs, " +
                    "utilizing a secure gateway API for seamless processing. This method enables users to access fitness content, " +
                    "ensuring a subscription model within our platform."
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
    @GetMapping("/{programId}/subscribe")
    public ModelAndView subscribeOnProgram(@PathVariable("programId") Integer programId){

        ModelAndView mv = new ModelAndView();

        User activeUser = sessionInfo.getCurrentUser();

        ProgramClient programClient = programClientService.subscribeUserOnProgram(activeUser.getId(), programId);

//        ProgramClientResponse program = programClientService.getProgramResponse(clientId, programId);
//
//        mv.addObject("program", program);

        mv.setViewName("redirect:/api/program-client/" + programClient.getId());

        return mv;

    }

    @Operation(
            summary = "Get all exercises of program API",
            description = "This method retrieves a comprehensive list of exercises associated with a specific fitness program in our system. " +
                    "This function allows seamless access to detailed information on all exercises included in the designated fitness program, " +
                    "facilitating personalized and effective workout routines for users."
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
    @GetMapping("/exercises/{exerciseId}")
    public ModelAndView getClientExercise(@PathVariable("exerciseId") Integer exerciseId){

        ModelAndView mv = new ModelAndView("main-ui/exercise-view");

        ExerciseContext exerciseContext = exerciseService.getExerciseContext(exerciseId);

        mv.addObject("exercise", exerciseContext);

        return mv;

    }

    @Operation(
            summary = "Record time of execution API",
            description = "The function captures and logs the time of execution for a specific exercise within our fitness system. " +
                    "This feature enables users to track and analyze their workout performance over time, " +
                    "providing valuable insights into exercise duration and progress within their fitness regimen."
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
    @ResponseBody
    @PostMapping("/exercises/recordTime/{exerciseId}")
    public String recordTime(@PathVariable("exerciseId") Integer exerciseId,
                             @RequestBody TimerRequestDto timerRequestDto){

        exerciseService.recordTime(exerciseId, timerRequestDto);

        return "/api/program-client/exercises/" + exerciseId;
    }

    @Operation(
            summary = "Complete program execution API",
            description = "The functionality grants users the capability to mark a fitness program as completed within our system. " +
                    "This feature empowers clients to track their journey, signaling the successful culmination of a specific program " +
                    "and facilitating a sense of accomplishment. Upon completion, users may receive relevant " +
                    "statistics or recommendations for further engagement with our fitness platform."
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
    @GetMapping("/{programId}/complete-program")
    public ModelAndView completeProgram(@PathVariable("programId") Integer programId){

        programClientService.completeProgram(programId);

        return new ModelAndView("redirect:/api/program-client/" + programId);
    }
}
