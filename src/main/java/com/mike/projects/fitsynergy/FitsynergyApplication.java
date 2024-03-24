package com.mike.projects.fitsynergy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
public class FitsynergyApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitsynergyApplication.class, args);
    }

}
