package com.mike.projects.fitsynergy.users.controller;

import com.mike.projects.fitsynergy.users.service.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(
        name = "APIs for client processes of FitSynergy",
        description = "APIs to CREATE, UPDATE, ENRICH, FETCH AND DELETE client"
)
@Controller
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;





}
