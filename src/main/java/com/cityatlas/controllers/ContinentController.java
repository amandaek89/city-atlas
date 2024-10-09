/* package com.cityatlas.controllers;

import com.cityatlas.dtos.ContinentDto;
import com.cityatlas.services.ContinentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/continents")
public class ContinentController {

    @Autowired
    private ContinentService continentService;

    @GetMapping("")
    public ResponseEntity<List<ContinentDto>> getAllContinents() {
        return ResponseEntity.ok(continentService.getAllContinents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContinentDto> getContinentById(@PathVariable long id) {
        return ResponseEntity.ok(continentService.getContinentById(id));
    }

    @PostMapping("")
    public ResponseEntity<ContinentDto> addContinent(@RequestBody ContinentDto continentDto) {
        return ResponseEntity.ok(continentService.addContinent(continentDto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ContinentDto> updateContinent(@PathVariable Long id, @RequestBody ContinentDto updatedContinentDto) {
        return ResponseEntity.ok(continentService.updateContinent(id, updatedContinentDto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> deleteContinent(@PathVariable Long id) {
        continentService.deleteContinent(id);
        return ResponseEntity.ok().build();
    }

}*/