package com.mike.projects.fitsynergy.users.mapper;

import com.mike.projects.fitsynergy.users.dto.UserRequestDto;
import com.mike.projects.fitsynergy.users.model.Client;
import com.mike.projects.fitsynergy.users.model.User;

public class UserMapper {
    public static void mapUser(User sourceObj, User targetObj){
        targetObj.setFirstName(sourceObj.getFirstName());
        targetObj.setLastName(sourceObj.getLastName());
        targetObj.setEmail(sourceObj.getEmail());
        targetObj.setPassword(sourceObj.getPassword());
        targetObj.setAge(sourceObj.getAge());
        targetObj.setGender(sourceObj.getGender());
    }

    public static void mapUserRequestDto(UserRequestDto dtoObj, User targetEntity){
        targetEntity.setFirstName(dtoObj.getFirstName());
        targetEntity.setLastName(dtoObj.getLastName());
        targetEntity.setEmail(dtoObj.getEmail());
        targetEntity.setPassword(dtoObj.getPassword());
        targetEntity.setAge(dtoObj.getAge());
        targetEntity.setGender(dtoObj.getGender());
    }
}
