package com.mike.projects.fitsynergy.program.service;

import com.mike.projects.fitsynergy.program.dto.OccupationRequestDto;
import com.mike.projects.fitsynergy.program.mapper.OccupationMapper;
import com.mike.projects.fitsynergy.program.model.Occupation;
import com.mike.projects.fitsynergy.program.repo.OccupationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OccupationService {

    private final OccupationRepo occupationRepo;

    public Occupation addOccupation(OccupationRequestDto requestDto){
        Occupation occupation = new Occupation();
        OccupationMapper.mapOccupationRequestDto(requestDto, occupation);
        return occupationRepo.save(occupation);
    }

    public List<Occupation> getAllOccupations(){
        return occupationRepo.findAll();
    }

    public Occupation getOccupationById(Integer occupationId) {
        return occupationRepo.findById(occupationId)
                .orElseThrow(() -> new RuntimeException("Internal Error. Occupation with id " + occupationId + " is not present"));
    }
}
