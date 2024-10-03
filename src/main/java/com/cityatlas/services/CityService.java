package com.cityatlas.services;

import com.cityatlas.dtos.CityDto; // Rättade DTO-namn
import com.cityatlas.mappers.CityMapper;
import com.cityatlas.models.City;
import com.cityatlas.models.Country;
import com.cityatlas.repositories.CityRepo;
import com.cityatlas.repositories.CountryRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final CityRepo cityRepo;
    private final CountryRepo countryRepo;

    public CityService(CityRepo cityRepo, CountryRepo countryRepo) {
        this.cityRepo = cityRepo;
        this.countryRepo = countryRepo;
    }

    // Hämtar alla städer
    public List<CityDto> getAllCities() {
        return cityRepo.findAll()
                .stream()
                .map(CityMapper::toDto) // Rättade toDTO till toDto
                .collect(Collectors.toList());
    }

    // Hämtar en stad baserat på ID
    public CityDto getCityById(Long id) {
        return cityRepo.findById(id)
                .map(CityMapper::toDto)
                .orElseThrow(() -> new RuntimeException("City not found"));
    }

    // Lägger till en ny stad
    public CityDto addCity(CityDto cityDto) {
        Country country = countryRepo.findById(cityDto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found"));
        City city = CityMapper.toEntity(cityDto, country);
        City savedCity = cityRepo.save(city);
        return CityMapper.toDto(savedCity);
    }

    // Uppdaterar en stad
    public CityDto updateCity(Long id, CityDto updatedCityDto) {
        Country country = countryRepo.findById(updatedCityDto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found"));

        return cityRepo.findById(id)
                .map(city -> {
                    city.setName(updatedCityDto.getName());
                    city.setPopulation(updatedCityDto.getPopulation());
                    city.setKnownFor(updatedCityDto.getKnownFor());
                    city.setCountry(country);
                    return CityMapper.toDto(cityRepo.save(city));
                })
                .orElseThrow(() -> new RuntimeException("City not found"));
    }

    // Tar bort en stad
    public void deleteCity(Long id) {
        cityRepo.deleteById(id);
    }
}
