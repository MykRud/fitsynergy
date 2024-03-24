package com.mike.projects.fitsynergy.program.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(
        name = "Activity Dashboard Response Dto",
        description = "Schema to hold main  analytics data"
)
@Data @Builder
public class ActivityDashboardResponseDto {

    // Weeks activity duration
    @Schema(
            description = "Execution Time of activities for Past Weak",
            example = "50 minutes"
    )
    private Integer executionTimePastWeak;
    @Schema(
            description = "Execution Time of activities for Current Weak",
            example = "60 minutes"
    )
    private Integer executionTimeCurrentWeak;
    @Schema(
            description = "Difference in percentage of executed time of activities for Current and Past Weaks",
            example = "80% more"
    )
    private Integer executionTimeDifferencePercentage;

    // Months activity numbers
    @Schema(
            description = "Executed activities in Past Month",
            example = "5"
    )
    private Integer executedActivitiesPastMonth;
    @Schema(
            description = "Executed activities in Current Month",
            example = "10"
    )
    private Integer executedActivitiesCurrentMonth;
    @Schema(
            description = "Difference in percentage of executed activities for Current and Past Weaks",
            example = "50% more"
    )
    private Integer executedActivitiesDifferencePercentage;

}
