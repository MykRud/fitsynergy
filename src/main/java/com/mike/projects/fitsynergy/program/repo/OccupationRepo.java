package com.mike.projects.fitsynergy.program.repo;

import com.mike.projects.fitsynergy.program.model.Occupation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccupationRepo extends JpaRepository<Occupation, Integer> {
}
