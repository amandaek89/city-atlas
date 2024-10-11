package com.cityatlas.services;

import com.cityatlas.dtos.ContinentDto;
import com.cityatlas.models.Continent;
import com.cityatlas.repositories.ContinentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContinentService {

    @Autowired
    private ContinentRepo continentRepo;

    public List<ContinentDto> getAllContinents() {
        return continentRepo.findAll().stream().map(ContinentDto::new).collect(Collectors.toList());
    }

    public Continent getContinentById(Long id) {
        return continentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Kontinenten med ID " + id + " hittades inte."));
    }


    public ContinentDto addContinent(ContinentDto continentDto) {
        return new ContinentDto(continentRepo.save(continentDto.toEntity()));
    }

    public ContinentDto updateContinent(Long id, ContinentDto updatedContinentDto) {
        return continentRepo.findById(id).map(continent -> {
            continent.setName(updatedContinentDto.getName());
            return new ContinentDto(continentRepo.save(continent));
        }).orElseThrow(() -> new RuntimeException("Continent not found"));
    }

    public void deleteContinent(Long id) {
        continentRepo.deleteById(id);
    }


}
