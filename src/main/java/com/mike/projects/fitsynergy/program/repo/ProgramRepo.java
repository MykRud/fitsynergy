package com.mike.projects.fitsynergy.program.repo;

import com.mike.projects.fitsynergy.program.model.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgramRepo extends JpaRepository<Program, Integer> {

    @Query("from program p where p.taken = false")
    Page<Program> findProgramNotTakenInLimit(Pageable page);

}
