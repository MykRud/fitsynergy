package com.mike.projects.fitsynergy.users.controller;

import com.mike.projects.fitsynergy.users.service.TrainerService;
import com.mike.projects.fitsynergy.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final TrainerService trainerService;
    private final UserService userService;

    @GetMapping("/users")
    public ModelAndView viewUsers(){

        ModelAndView mv = new ModelAndView("main-ui/users-list");

        mv.addObject("users", userService.getUsers(15));

        return mv;
    }

    @GetMapping("/set-trainer/{userId}")
    public ModelAndView setTrainer(@PathVariable("userId") Integer userId){
        trainerService.setTrainer(userId);

        return new ModelAndView("redirect:/api/admin/users");
    }

    @GetMapping("/unset-trainer/{userId}")
    public ModelAndView unsetTrainer(@PathVariable("userId") Integer userId){
        trainerService.unsetTrainer(userId);

        return new ModelAndView("redirect:/api/admin/users");
    }
}
