package com.mike.projects.fitsynergy.program.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
        name = "Program Request Dto",
        description = "Schema to hold main program's data"
)
@Data
public class ProgramRequestDto {
    @Schema(
            description = "Name of program",
            example = "Pull Ups program"
    )
    private String programName;
    @Schema(
            description = "Holding occupation id retrieved from frontend",
            example = "1"
    )
    private String occupationId;
    @Schema(
            description = "Level of program",
            example = "EASY, MEDIUM, HARD, VERY HARD"
    )
    private String level;
}
