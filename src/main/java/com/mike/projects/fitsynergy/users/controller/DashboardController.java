package com.mike.projects.fitsynergy.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mike.projects.fitsynergy.config.SessionInfo;
import com.mike.projects.fitsynergy.program.dto.ActivityDashboardResponseDto;
import com.mike.projects.fitsynergy.program.dto.DaysTimeDashboardDao;
import com.mike.projects.fitsynergy.program.dto.ProgramResponseDto;
import com.mike.projects.fitsynergy.program.model.ExerciseContext;
import com.mike.projects.fitsynergy.program.model.Program;
import com.mike.projects.fitsynergy.program.model.ProgramClient;
import com.mike.projects.fitsynergy.program.service.ProgramClientService;
import com.mike.projects.fitsynergy.program.service.ProgramService;
import com.mike.projects.fitsynergy.users.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Tag(
        name = "APIs for Main User's Dashboard of FitSynergy",
        description = "APIs for working with user's Dashboard with different abilities and analytics (for client, trainer and admin)"
)
@Controller
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final SessionInfo sessionInfo;
    private final ProgramClientService programClientService;
    private final ProgramService programService;

    @Operation(
            summary = "Get Main Dashboard and its Analytics API",
            description = "The main dashboard serves as the central hub for users, providing a comprehensive overview of their fitness journey. " +
                    "It prominently displays currently active programs, allowing users to seamlessly access ongoing training regimens. " +
                    "Additionally, the dashboard features a section dedicated to upcoming exercises, ensuring users stay informed about their upcoming workout routines. " +
                    "Furthermore, users can conveniently review their completed exercises, " +
                    "fostering a sense of accomplishment and providing a visual representation of their progress. The inclusion of robust " +
                    "analytics on the main dashboard empowers users with insightful data, including performance metrics, trends, and achievements, " +
                    "fostering a data-driven approach to fitness and enhancing the overall user experience within our system."
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
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mainDashboard(HttpSession httpSession) throws Exception{
        User activeUser = sessionInfo.getCurrentUser();

        System.out.println("User: " + activeUser.getEmail());

        ModelAndView mv = new ModelAndView("main-ui/index");

        // Weeks and months activity stats
        ActivityDashboardResponseDto stats = programClientService.getStatisticsExecutionOfActivities(activeUser.getId());

        // Weeks activity time
        mv.addObject("executionTime_pastWeak", stats.getExecutionTimePastWeak());
        mv.addObject("executionTime_currentWeak", stats.getExecutionTimeCurrentWeak());
        mv.addObject("executionDifferencePercentage", stats.getExecutionTimeDifferencePercentage());

        // Months activity numbers
        mv.addObject("executedActivities_pastMonth", stats.getExecutedActivitiesPastMonth());
        mv.addObject("executedActivities_currentMonth", stats.getExecutedActivitiesCurrentMonth());
        mv.addObject("executedActivities_differencePercentage", stats.getExecutedActivitiesDifferencePercentage());

        DaysTimeDashboardDao daysStatisticsExecutionOfActivities = programClientService.getDaysStatisticsExecutionOfActivities(activeUser.getId());

        mv.addObject("daysStats_days", daysStatisticsExecutionOfActivities.getDays());
        mv.addObject("daysStats_time", daysStatisticsExecutionOfActivities.getTime());

        List<ExerciseContext> incomingExercises = programClientService.getIncomingExercisesByClientId(activeUser.getId(), 4);
        mv.addObject("incomingExercises", incomingExercises);

        List<ProgramResponseDto> activePrograms = programClientService.getActiveProgramsWithActiveExercisesInLimit(activeUser.getId(), 4);
        mv.addObject("activePrograms", activePrograms);

        List<Program> popularPrograms = programService.getIncomingProgramsInLimitNotTaken(4);
        mv.addObject("popularPrograms", popularPrograms);

        return mv;
    }
}
