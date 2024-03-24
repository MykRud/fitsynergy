package com.mike.projects.fitsynergy.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Log In Request Dto",
        description = "Schema to hold log in user's data"
)
@Data
public class LogInRequestDto {
    @Schema(
            description = "Holding email of user",
            example = "23123091@gmail.com"
    )
    @Email
    private String email;
    @Schema(
            description = "Holding password of user",
            example = "werfs3rwf"
    )
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "password must contain at least eight characters, at least one number")
    private String password;
}
