package com.mike.projects.fitsynergy.program.mapper;

import com.mike.projects.fitsynergy.program.dto.ProgramRequestDto;
import com.mike.projects.fitsynergy.program.dto.ProgramResponseDto;
import com.mike.projects.fitsynergy.program.model.Program;
import com.mike.projects.fitsynergy.program.model.ProgramClient;

public class ProgramMapper {
    public static ProgramResponseDto mapProgramResponseDto(ProgramClient targetEntity){
        return ProgramResponseDto.builder()
                .id(targetEntity.getId())
                .name(targetEntity.getProgram().getName())
                .level(targetEntity.getProgram().getLevel())
                .occupation(targetEntity.getProgram().getOccupation())
                .startDate(targetEntity.getStartDate())
                .completeDate(targetEntity.getCompleteDate())
                .completed(targetEntity.getCompleted())
                .client(targetEntity.getClient())
                .exercises(targetEntity.getProgram().getExercises())
                .build();
    }
}
