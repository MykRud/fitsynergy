package com.mike.projects.fitsynergy.users.mapper;

import com.mike.projects.fitsynergy.users.model.Trainer;

public class TrainerMapper {
    public static void mapTrainer(Trainer sourceObj, Trainer targetObj){
        targetObj.setFirstName(sourceObj.getFirstName());
        targetObj.setLastName(sourceObj.getLastName());
        targetObj.setAge(sourceObj.getAge());
    }
}
