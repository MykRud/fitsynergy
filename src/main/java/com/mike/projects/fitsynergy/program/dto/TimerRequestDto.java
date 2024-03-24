package com.mike.projects.fitsynergy.program.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Schema(
        name = "Timer Request Dto",
        description = "Schema to hold timer data"
)
@Data
public class TimerRequestDto {
    @Schema(
            description = "Holding execution time of exercise",
            example = "20 minutes"
    )
    private Long executionTime;
    @Schema(
            description = "Start date of execution",
            example = "2024-02-01"
    )
    private Date startDate;
}
