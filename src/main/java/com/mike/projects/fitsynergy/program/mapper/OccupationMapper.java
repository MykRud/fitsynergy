package com.mike.projects.fitsynergy.program.mapper;

import com.mike.projects.fitsynergy.program.dto.OccupationRequestDto;
import com.mike.projects.fitsynergy.program.model.Occupation;
import com.mike.projects.fitsynergy.users.dto.UserRequestDto;
import com.mike.projects.fitsynergy.users.model.Client;

public class OccupationMapper {
    public static void mapOccupation(Occupation sourceObj, Occupation targetObj){
        targetObj.setName(sourceObj.getName());
    }

    public static void mapOccupationRequestDto(OccupationRequestDto dtoObj, Occupation targetEntity){
        targetEntity.setName(dtoObj.getName());
    }
}
