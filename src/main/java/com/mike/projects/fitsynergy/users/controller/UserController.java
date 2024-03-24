package com.mike.projects.fitsynergy.users.controller;

import com.mike.projects.fitsynergy.config.SessionInfo;
import com.mike.projects.fitsynergy.users.dto.UserRequestDto;
import com.mike.projects.fitsynergy.users.dto.LogInRequestDto;
import com.mike.projects.fitsynergy.users.model.User;
import com.mike.projects.fitsynergy.users.service.UserService;
import com.mike.projects.fitsynergy.users.utils.ErrorValidatorUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Tag(
        name = "APIs for general user processes of FitSynergy",
        description = "APIs to AUTHENTICATE, AUTHORIZATE, REGISTER, CREATE, UPDATE, ENRICH, FETCH AND DELETE user"
)
@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "API to log in into FitSynergy",
            description = "API allows users to securely access our system by providing valid credentials. " +
                    "This authentication process verifies user identity, granting authorized access to personalized " +
                    "fitness profiles, training history, and other user-specific data within the platform."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error"
            )
    })
    @GetMapping("/log-in")
    public ModelAndView logInPage(){
        ModelAndView mv = new ModelAndView("login");
//        mv.addObject("user", new LogInRequestDto());
        return mv;
    }

    @Operation(
            summary = "Get registration page API",
            description = "API to get registration page view to create new user inside FitSynergy system"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error"
            )
    })
    @GetMapping("/sign-up")
    public ModelAndView registerNewClientPage(){
        ModelAndView mv = new ModelAndView("signup");
        mv.addObject("user", new UserRequestDto());
        return mv;
    }

    @Operation(
            summary = "Logic of registration API",
            description = "API that enables users to create an account within our system by supplying essential " +
                    "information such as username, email, and password. Upon successful registration, " +
                    "users gain access to personalized features, including the ability to set fitness goals, " +
                    "track progress, and engage with various programs and exercises offered on our platform."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error"
            )
    })
    @PostMapping("/sign-up")
    public ModelAndView registerNewClientLogic(@Valid @ModelAttribute UserRequestDto requestDto,
                                               BindingResult bindingResult, HttpSession httpSession){
        ModelAndView mv = new ModelAndView("redirect:/api/users/login");

        // Validation
        Map<String, String> errors = ErrorValidatorUtil.validateDto(bindingResult);
        if(!errors.isEmpty()){
            mv.addObject("errors", errors);
            return mv;
        }

        User user = userService.registerUser(requestDto);

        return mv;
    }

}
