package com.mike.projects.fitsynergy.program.dto;

import com.mike.projects.fitsynergy.program.model.ExerciseContext;
import com.mike.projects.fitsynergy.program.model.Level;
import com.mike.projects.fitsynergy.program.model.Occupation;
import com.mike.projects.fitsynergy.program.model.Program;
import com.mike.projects.fitsynergy.users.model.Client;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Schema(
        name = "Program Response Dto",
        description = "Schema to hold main program response's data"
)
@Data @Builder
public class ProgramResponseDto {
    @Schema(
            description = "Holding program id retrieved from frontend",
            example = "1"
    )
    private Integer id;
    @Schema(
            description = "Name of program",
            example = "Pull Ups Program"
    )
    private String name;
    @Schema(
            description = "Holding an occupation",
            example = "Gymnastics"
    )
    private Occupation occupation;
    @Schema(
            description = "Level of program",
            example = "EASY, MEDIUM, HARD, VERY HARD"
    )
    private Level level;
    @Schema(
            description = "Exercise: Starting date of execution",
            example = "2024-02-01"
    )
    private Date startDate;
    @Schema(
            description = "Exercise: Complete date of execution",
            example = "2024-02-01"
    )
    private Date completeDate;
    @Schema(
            description = "Is exercise completed or not",
            example = "false"
    )
    private Boolean completed;
    @Schema(
            description = "Holding an client details",
            example = "Misha Rudyk"
    )
    private Client client;
    @Schema(
            description = "Holding desired exercises of program (incoming / active / completed etc)",
            example = "Pull Ups, Push Ups, Straips"
    )
    private List<ExerciseContext> exercises;
}
