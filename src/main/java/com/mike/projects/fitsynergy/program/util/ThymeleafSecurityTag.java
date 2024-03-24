package com.mike.projects.fitsynergy.program.util;

import com.mike.projects.fitsynergy.config.SessionInfo;
import com.mike.projects.fitsynergy.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ThymeleafSecurityTag {

    private final SessionInfo sessionInfo;

    public boolean isSessionUserHasRole(String ... roles){
        User currentUser = sessionInfo.getCurrentUser();

        String userRole = currentUser.getUserDetails().getRole().name();
        for(String role : roles){
            if (userRole.equalsIgnoreCase(role)){
                return true;
            }
        }
        return false;
    }
}
