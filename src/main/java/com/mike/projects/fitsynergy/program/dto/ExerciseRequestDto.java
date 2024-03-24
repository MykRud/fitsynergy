package com.mike.projects.fitsynergy.program.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Schema(
        name = "Exercise Request Dto",
        description = "Schema to hold main exercise's data"
)
@Data
public class ExerciseRequestDto {
    @Schema(
            description = "Name of exercise",
            example = "Pull Ups"
    )
    private String name;
    @Schema(
            description = "Exercise: Number of Sets",
            example = "5"
    )
    private Integer sets;
    @Schema(
            description = "Exercise: Number of Repetitions",
            example = "15"
    )
    private Integer reps;
    @Schema(
            description = "Exercise: estimate execution time",
            example = "50 minutes"
    )
    private Integer time;
    @Schema(
            description = "Exercise: Starting date of execution",
            example = "2024-02-01"
    )
    private Date date;
    @Schema(
            description = "Exercise: Youtube link to show video of exercise execution",
            example = "5"
    )
    private String videoLink;
}
