package com.mike.projects.fitsynergy.program.repo;

import com.mike.projects.fitsynergy.program.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExerciseRepo extends JpaRepository<Exercise, Integer> {

    Optional<Exercise> findByName(String name);

}
