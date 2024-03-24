package com.mike.projects.fitsynergy.program.service;

import com.mike.projects.fitsynergy.program.model.Program;
import com.mike.projects.fitsynergy.program.repo.ProgramRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepo programRepo;

    public Program saveProgram(Program program) {
        return programRepo.save(program);
    }

    public Program getProgramById(Integer programId){
        return programRepo.findById(programId)
                .orElseThrow(() -> new RuntimeException("Cannot find program with id " + programId));
    }

    public List<Program> getIncomingProgramsInLimitNotTaken(Integer limit){
        return programRepo.findProgramNotTakenInLimit(PageRequest.of(0, limit).withSort(Sort.Direction.DESC, "id"))
                .stream()
                .toList();
    }
}
