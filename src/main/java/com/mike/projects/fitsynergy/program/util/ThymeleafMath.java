package com.mike.projects.fitsynergy.program.util;

import org.springframework.stereotype.Component;

@Component
public class ThymeleafMath {
    public long floor(double n){
        return (long) Math.floor(n);
    }
}
