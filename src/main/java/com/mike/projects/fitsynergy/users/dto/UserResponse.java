package com.mike.projects.fitsynergy.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class UserResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private String gender;
    private String role;
}
