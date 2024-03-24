package com.mike.projects.fitsynergy.program.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Schema(
        name = "Graph Dashboard Response Dto",
        description = "Schema to hold main data for graph"
)
@Data @NoArgsConstructor @AllArgsConstructor
public class DaysTimeDashboardDao {
    @Schema(
            description = "Days to show on graph",
            example = "01 March, 04 March, 05 March"
    )
    private List<String> days;
    @Schema(
            description = "Execution time to show on graph",
            example = "40 minutes, 25 minutes, 55 minutes"
    )
    private List<Integer> time;
}
