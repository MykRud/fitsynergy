package com.mike.projects.fitsynergy.users.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ErrorValidatorUtil {
    public static Map<String, String> validateDto(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()){
            for (FieldError error : bindingResult.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());
            }
        }
        return errors;
    }
}
