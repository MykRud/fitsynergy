package com.mike.projects.fitsynergy.program.service;

import com.mike.projects.fitsynergy.program.dto.ActivityDashboardResponseDto;
import com.mike.projects.fitsynergy.program.dto.DaysTimeDashboardDao;
import com.mike.projects.fitsynergy.program.dto.ProgramResponseDto;
import com.mike.projects.fitsynergy.program.mapper.ProgramMapper;
import com.mike.projects.fitsynergy.program.model.ExerciseContext;
import com.mike.projects.fitsynergy.program.model.Program;
import com.mike.projects.fitsynergy.program.model.ProgramClient;
import com.mike.projects.fitsynergy.program.repo.ProgramClientRepo;
import com.mike.projects.fitsynergy.users.model.Client;
import com.mike.projects.fitsynergy.users.service.ClientService;
import com.mike.projects.fitsynergy.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramClientService {

    private final ProgramClientRepo programClientRepo;
    private final ProgramService programService;
    private final ClientService clientService;

    public List<ProgramResponseDto> getAllClientPrograms(Integer userId){
        List<ProgramClient> allPrograms = programClientRepo.findAllByClientId(userId);

        List<ProgramResponseDto> programResponseDtos = new ArrayList<>();

        for(ProgramClient pc : allPrograms){
            List<ExerciseContext> exercises = pc.getProgram().getExercises().stream()
                    .sorted((e1, e2) -> e2.getStartDate().compareTo(e1.getStartDate()))
                    .limit(1)
                    .toList();
            programResponseDtos.add(
                    ProgramResponseDto.builder()
                            .id(pc.getId())
                            .name(pc.getProgram().getName())
                            .level(pc.getProgram().getLevel())
                            .occupation(pc.getProgram().getOccupation())
                            .completed(pc.getCompleted())
                            .exercises(exercises)
                            .build()
            );
        }

        programResponseDtos = programResponseDtos.stream()
                .sorted((p1, p2) -> {
                    if (!p2.getExercises().isEmpty() && !p1.getExercises().isEmpty()) {
                        return p2.getExercises().get(0).getStartDate().compareTo(p1.getExercises().get(0).getStartDate());
                    } else {
                        return Integer.compare(p2.getExercises().size(), p1.getExercises().size());
                    }
                })
                .toList();

        return programResponseDtos;
    }

    public List<ExerciseContext> getIncomingExercisesByClientId(Integer clientId, int numberOfExercises){
        List<ProgramClient> allClientPrograms = getActivePrograms(clientId);
        return allClientPrograms.stream()
                .map(pc -> pc.getProgram().getExercises())
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(ExerciseContext::getStartDate))
                .limit(numberOfExercises)
                .collect(Collectors.toList());
    }

    public List<ExerciseContext> getIncomingExercisesOfProgram(Integer programClientId, int numberOfExercises){
        List<ExerciseContext> exercisesFromDb = programClientRepo.findAllByIdAndCompletedExercises(programClientId, false);
        List<ExerciseContext> exercises = exercisesFromDb.stream()
                .sorted(Comparator.comparing(ExerciseContext::getStartDate))
                .limit(numberOfExercises)
                .collect(Collectors.toList());
        Collections.reverse(exercises);
        return exercises;
    }

    public List<ProgramResponseDto> getActiveProgramsWithActiveExercisesInLimit(Integer clientId, int numberOfPrograms) {
        List<ProgramResponseDto> responsesList = new ArrayList<>();

        List<ProgramClient> activePrograms = getActiveProgramsInLimit(clientId, numberOfPrograms);

        for (ProgramClient programClient : activePrograms){
            ProgramResponseDto responseDto = ProgramMapper.mapProgramResponseDto(programClient);
            List<ExerciseContext> activeExercises = programClient.getProgram().getExercises().stream()
                    .filter(Predicate.not(ExerciseContext::getCompleted))
                    .toList();
            responseDto.setExercises(activeExercises);
            responsesList.add(responseDto);
        }

        return responsesList;
    }

    public List<ProgramClient> getActiveProgramsInLimit(Integer clientId, int numberOfPrograms) {
        return getActivePrograms(clientId).stream()
                .sorted((o1, o2) -> o2.getStartDate().compareTo(o1.getStartDate()))
                .limit(numberOfPrograms)
                .collect(Collectors.toList());
    }

    public List<ProgramClient> getActivePrograms(Integer clientId){
        return programClientRepo.findAllByClientIdAndCompleted(clientId, false);
    }

    public ActivityDashboardResponseDto getStatisticsExecutionOfActivities(Integer clientId){
        List<ExerciseContext> allProgramsExercises = programClientRepo.findAllByClientIdAndCompletedExercises(clientId, true);

        // Default settings for region
        ZoneId zoneId = ZoneId.of("Europe/Kyiv");
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(LocalDateTime.now());

        // Weeks activity duration
        Instant currentMonday = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).atStartOfDay().toInstant(zoneOffset);
        Instant pastMonday = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).minus(1, ChronoUnit.WEEKS).atStartOfDay().toInstant(zoneOffset);

        Integer executionTimePastWeak = (int) allProgramsExercises
                .stream()
                .filter(ec -> !ec.getStartDate().toLocalDate().atStartOfDay(zoneId).toInstant().isBefore(pastMonday) &&
                        ec.getStartDate().toLocalDate().atStartOfDay(zoneId).toInstant().isBefore(currentMonday))
                .mapToDouble(ExerciseContext::getExecutionTime)
                .sum();

        Integer executionTimeCurrentWeak = (int) allProgramsExercises
                .stream()
                .filter(ec -> !ec.getStartDate().toLocalDate().atStartOfDay(zoneId).toInstant().isBefore(currentMonday))
                .mapToDouble(ExerciseContext::getExecutionTime)
                .sum();

        Integer executionTimeDifferencePercentage = 0;
        if((executionTimeCurrentWeak + executionTimePastWeak) != 0)
            executionTimeDifferencePercentage =
                (executionTimeCurrentWeak * 100) / (executionTimeCurrentWeak + executionTimePastWeak) -
                        (executionTimePastWeak * 100) / (executionTimeCurrentWeak + executionTimePastWeak);

        // Months activity numbers
        Instant firstDayOfCurrentMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay().toInstant(zoneOffset);
        Instant firstDayOfPastMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).minus(1, ChronoUnit.MONTHS).atStartOfDay().toInstant(zoneOffset);

        Integer executedActivitiesPastMonth = (int) allProgramsExercises
                .stream()
                .filter(ec -> !ec.getStartDate().toLocalDate().atStartOfDay(zoneId).toInstant().isBefore(firstDayOfPastMonth) &&
                        ec.getStartDate().toLocalDate().atStartOfDay(zoneId).toInstant().isBefore(firstDayOfCurrentMonth))
                .count();

        Integer executedActivitiesCurrentMonth = (int) allProgramsExercises
                .stream()
                .filter(ec -> !ec.getStartDate().toLocalDate().atStartOfDay(zoneId).toInstant().isBefore(firstDayOfCurrentMonth))
                .count();

        Integer executedActivitiesDifferencePercentage = 0;
        if((executedActivitiesCurrentMonth + executedActivitiesPastMonth) != 0)
            executedActivitiesDifferencePercentage =
                (executedActivitiesCurrentMonth * 100) / (executedActivitiesCurrentMonth + executedActivitiesPastMonth) -
                        (executedActivitiesPastMonth * 100) / (executedActivitiesCurrentMonth + executedActivitiesPastMonth);

        return ActivityDashboardResponseDto.builder()
                .executionTimeCurrentWeak(executionTimeCurrentWeak)
                .executionTimePastWeak(executionTimePastWeak)
                .executionTimeDifferencePercentage(executionTimeDifferencePercentage)
                .executedActivitiesCurrentMonth(executedActivitiesCurrentMonth)
                .executedActivitiesPastMonth(executedActivitiesPastMonth)
                .executedActivitiesDifferencePercentage(executedActivitiesDifferencePercentage)
                .build();
    }

    public ProgramClient subscribeUserOnProgram(Integer userId, Integer programId){
        ProgramClient pc = new ProgramClient();

        Client client = clientService.getClientById(userId);
        Program program = programService.getProgramById(programId);

        Program cloneProgram = null;

        try{
            cloneProgram = program.clone();
            cloneProgram.setTaken(true);
            programService.saveProgram(cloneProgram);
        } catch (CloneNotSupportedException ex){
            throw new RuntimeException("Cannot clone program " + program.getId());
        }

        pc.setClient(client);
        pc.setProgram(cloneProgram);
        pc.setStartDate(Date.valueOf(LocalDateTime.now().toLocalDate()));
        pc.setCompleted(false);

        return programClientRepo.save(pc);
    }

    public ProgramClient getClientProgram(Integer programClientId) {
        return programClientRepo.findById(programClientId)
                .orElseThrow(() -> new RuntimeException("Cannot fond program with id " + programClientId));
    }

    public ActivityDashboardResponseDto getStatisticsOfProgram(Integer programId){
        ProgramClient clientProgram = getClientProgram(programId);

        List<ExerciseContext> exercises = clientProgram.getProgram().getExercises()
                .stream()
                .filter(ExerciseContext::getCompleted)
                .toList();

        // Default settings for region
        ZoneId zoneId = ZoneId.of("Europe/Kyiv");
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(LocalDateTime.now());

        // Weeks activity duration
        Instant currentMonday = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).atStartOfDay().toInstant(zoneOffset);
        Instant pastMonday = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).minus(1, ChronoUnit.WEEKS).atStartOfDay().toInstant(zoneOffset);

        Integer executionTimePastWeak = (int) exercises
                .stream()
                .filter(ec -> !ec.getStartDate().toLocalDate().atStartOfDay(zoneId).toInstant().isBefore(pastMonday) &&
                        ec.getStartDate().toLocalDate().atStartOfDay(zoneId).toInstant().isBefore(currentMonday))
                .mapToDouble(ExerciseContext::getExecutionTime)
                .sum();

        Integer executionTimeCurrentWeak = (int) exercises
                .stream()
                .filter(ec -> !ec.getStartDate().toLocalDate().atStartOfDay(zoneId).toInstant().isBefore(currentMonday))
                .mapToDouble(ExerciseContext::getExecutionTime)
                .sum();

        Integer executionTimeDifferencePercentage = 0;
        if((executionTimeCurrentWeak + executionTimePastWeak) != 0)
            executionTimeDifferencePercentage =
                    (executionTimeCurrentWeak * 100) / (executionTimeCurrentWeak + executionTimePastWeak) -
                            (executionTimePastWeak * 100) / (executionTimeCurrentWeak + executionTimePastWeak);

        // Months activity numbers
        Instant firstDayOfCurrentMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay().toInstant(zoneOffset);
        Instant firstDayOfPastMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).minus(1, ChronoUnit.MONTHS).atStartOfDay().toInstant(zoneOffset);

        Integer executedActivitiesPastMonth = (int) exercises
                .stream()
                .filter(ec -> !ec.getStartDate().toLocalDate().atStartOfDay(zoneId).toInstant().isBefore(firstDayOfPastMonth) &&
                        ec.getStartDate().toLocalDate().atStartOfDay(zoneId).toInstant().isBefore(firstDayOfCurrentMonth))
                .count();

        Integer executedActivitiesCurrentMonth = (int) exercises
                .stream()
                .filter(ec -> !ec.getStartDate().toLocalDate().atStartOfDay(zoneId).toInstant().isBefore(firstDayOfCurrentMonth))
                .count();

        Integer executedActivitiesDifferencePercentage = 0;
        if((executedActivitiesCurrentMonth + executedActivitiesPastMonth) != 0)
            executedActivitiesDifferencePercentage =
                    (executedActivitiesCurrentMonth * 100) / (executedActivitiesCurrentMonth + executedActivitiesPastMonth) -
                            (executedActivitiesPastMonth * 100) / (executedActivitiesCurrentMonth + executedActivitiesPastMonth);

        return ActivityDashboardResponseDto.builder()
                .executionTimeCurrentWeak(executionTimeCurrentWeak)
                .executionTimePastWeak(executionTimePastWeak)
                .executionTimeDifferencePercentage(executionTimeDifferencePercentage)
                .executedActivitiesCurrentMonth(executedActivitiesCurrentMonth)
                .executedActivitiesPastMonth(executedActivitiesPastMonth)
                .executedActivitiesDifferencePercentage(executedActivitiesDifferencePercentage)
                .build();
    }

    public List<ExerciseContext> getCompletedExercisesCompletedSortByDate(Integer programClientId, int numberOfExercises) {
        ProgramClient clientProgram = getClientProgram(programClientId);
        return clientProgram.getProgram().getExercises().stream()
                .filter(ExerciseContext::getCompleted)
                .sorted(Comparator.comparing(ExerciseContext::getStartDate))
                .limit(numberOfExercises)
                .collect(Collectors.toList());
    }

    public void completeProgram(Integer programClientId) {
        ProgramClient clientProgram = getClientProgram(programClientId);
        clientProgram.setCompleted(true);
        clientProgram.setCompleteDate(Date.valueOf(LocalDate.now()));
        programClientRepo.save(clientProgram);
    }

    public DaysTimeDashboardDao getDaysStatisticsExecutionOfActivities(Integer clientId){
        List<ExerciseContext> allProgramsExercises = programClientRepo.findAllByClientIdAndCompletedExercises(clientId, true)
                .stream()
                .sorted(Comparator.comparing(ExerciseContext::getStartDate))
                .limit(8)
                .toList();

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM", Locale.getDefault());

        List<String> days = new ArrayList<>();
        List<Integer> time = new ArrayList<>();

        for (ExerciseContext exercise : allProgramsExercises){
            days.add(sdf.format(exercise.getStartDate()));
            time.add((int) Math.floor(exercise.getExecutionTime()));
        }

        return new DaysTimeDashboardDao(days, time);
    }

    public DaysTimeDashboardDao getDaysStatisticsExecutionOfActivitiesByProgramId(Integer programId){
        List<ExerciseContext> allProgramsExercises = programClientRepo.findAllByIdAndCompletedExercises(programId, true)
                .stream()
                .sorted(Comparator.comparing(ExerciseContext::getStartDate))
                .limit(8)
                .toList();

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM", Locale.getDefault());

        List<String> days = new ArrayList<>();
        List<Integer> time = new ArrayList<>();

        for (ExerciseContext exercise : allProgramsExercises){
            days.add(sdf.format(exercise.getStartDate()));
            time.add((int) Math.floor(exercise.getExecutionTime()));
        }

        return new DaysTimeDashboardDao(days, time);
    }
}
