package com.mike.projects.fitsynergy.program.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(
        name = "APIs for exercise' based CRUD of FitSynergy",
        description = "APIs for CRUD to CREATE, UPDATE, ENRICH, FETCH AND DELETE exercises (for trainer and admin)"
)
@Controller
@RequestMapping("/api/exercises")
public class ExerciseController {
}
