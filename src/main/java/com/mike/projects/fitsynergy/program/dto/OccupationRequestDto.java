package com.mike.projects.fitsynergy.program.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
        name = "Occupation Request Dto",
        description = "Schema to hold main occupation's data"
)
@Data
public class OccupationRequestDto {
    @Schema(
            description = "Name of occupation",
            example = "Gymnastics"
    )
    private String name;
}
