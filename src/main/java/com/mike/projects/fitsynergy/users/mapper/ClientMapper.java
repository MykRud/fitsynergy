package com.mike.projects.fitsynergy.users.mapper;

import com.mike.projects.fitsynergy.users.dto.UserRequestDto;
import com.mike.projects.fitsynergy.users.model.Client;

public class ClientMapper {
    public static void mapClient(Client sourceObj, Client targetObj){
        targetObj.setFirstName(sourceObj.getFirstName());
        targetObj.setLastName(sourceObj.getLastName());
        targetObj.setEmail(sourceObj.getEmail());
        targetObj.setPassword(sourceObj.getPassword());
        targetObj.setAge(sourceObj.getAge());
    }

    public static void mapClientRequestDto(UserRequestDto dtoObj, Client targetEntity){
        targetEntity.setFirstName(dtoObj.getFirstName());
        targetEntity.setLastName(dtoObj.getLastName());
        targetEntity.setEmail(dtoObj.getEmail());
        targetEntity.setPassword(dtoObj.getPassword());
        targetEntity.setAge(dtoObj.getAge());
    }
}
