package com.mike.projects.fitsynergy.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Schema(
        name = "Registration Request Dto",
        description = "Schema to hold user's registration data"
)
@Data
public class UserRequestDto {
    @Schema(
            description = "Holding user's first name",
            example = "Misha"
    )
    @Size(min = 2, max = 100, message = "first name should be in range between 2 and 100 characters")
    private String firstName;
    @Schema(
            description = "Holding user's last name",
            example = "Rudyk"
    )
    @Size(min = 2, max = 100, message = "last name should be in range between 2 and 100 characters")
    private String lastName;
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
    @Schema(
            description = "Holding user's age",
            example = "21"
    )
    @Min(value = 15, message = "your age is under 15")
    @Max(value = 90, message = "your age is over 90")
    private Integer age;
    @Schema(
            description = "Holding user's gender",
            example = "male, female"
    )
    @Pattern(regexp = "^(male|female)")
    private String gender;
}
