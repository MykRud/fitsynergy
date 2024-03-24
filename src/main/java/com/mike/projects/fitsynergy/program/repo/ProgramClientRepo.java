package com.mike.projects.fitsynergy.program.repo;

import com.mike.projects.fitsynergy.program.model.ExerciseContext;
import com.mike.projects.fitsynergy.program.model.Program;
import com.mike.projects.fitsynergy.program.model.ProgramClient;
import com.mike.projects.fitsynergy.users.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ProgramClientRepo extends JpaRepository<ProgramClient, Integer> {

    @Query("from program_client where client.id=?1")
    List<ProgramClient> findAllByClientId(Integer clientId);
    @Query("from program_client pc where pc.client.id=?1 and pc.completed=?2")
    List<ProgramClient> findAllByClientIdAndCompleted(Integer clientId, Boolean isCompleted);
    @Query("select e from program_client pc left join program.exercises e where pc.client.id=?1 and e.completed=?2")
    List<ExerciseContext> findAllByClientIdAndCompletedExercises(Integer clientId, Boolean isCompleted);
    @Query("select e from program_client pc left join program.exercises e where pc.id=?1 and e.completed=?2")
    List<ExerciseContext> findAllByIdAndCompletedExercises(Integer id, Boolean isCompleted);

    @Query("select e from program_client pc left join program.exercises e where e.completed=true order by e.startDate DESC limit 7")
    List<ExerciseContext> findAllByProgramExercisesStartDateBetween();
}
