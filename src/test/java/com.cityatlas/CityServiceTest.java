package com.cityatlas;

import com.cityatlas.dtos.CityDto;
import com.cityatlas.mappers.CityMapper;
import com.cityatlas.models.City;
import com.cityatlas.models.Country;
import com.cityatlas.repositories.CityRepo;
import com.cityatlas.repositories.CountryRepo;
import com.cityatlas.services.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CityServiceTest {

    @Mock
    private CityRepo cityRepo;

    @Mock
    private CountryRepo countryRepo;

    @InjectMocks
    private CityService cityService;

    private City city;
    private Country country;
    private CityDto cityDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup a sample city and country for testing
        country = new Country();
        country.setId(1L);
        country.setName("Sample Country");

        city = new City();
        city.setId(1L);
        city.setName("Sample City");
        city.setPopulation(100000);
        city.setKnownFor("Famous for testing");
        city.setCountry(country);

        cityDto = new CityDto();
        cityDto.setId(1L);
        cityDto.setName("Sample City");
        cityDto.setPopulation(100000);
        cityDto.setKnownFor("Famous for testing");
        cityDto.setCountryId(1L);
    }

    @Test
    public void testGetAllCities() {
        when(cityRepo.findAll()).thenReturn(Arrays.asList(city));

        List<CityDto> cities = cityService.getAllCities();

        assertEquals(1, cities.size());
        assertEquals(city.getName(), cities.get(0).getName());
        verify(cityRepo, times(1)).findAll();
    }

    @Test
    public void testGetCityById() {
        when(cityRepo.findById(anyLong())).thenReturn(Optional.of(city));

        CityDto result = cityService.getCityById(1L);

        assertEquals(city.getName(), result.getName());
        verify(cityRepo, times(1)).findById(1L);
    }

    @Test
    public void testAddCity() {
        when(countryRepo.findById(anyLong())).thenReturn(Optional.of(country));
        when(cityRepo.save(any(City.class))).thenReturn(city);

        CityDto result = cityService.addCity(cityDto);

        assertEquals(city.getName(), result.getName());
        verify(countryRepo, times(1)).findById(1L);
        verify(cityRepo, times(1)).save(any(City.class));
    }

    @Test
    public void testUpdateCity() {
        when(cityRepo.findById(anyLong())).thenReturn(Optional.of(city));
        when(countryRepo.findById(anyLong())).thenReturn(Optional.of(country));
        when(cityRepo.save(any(City.class))).thenReturn(city);

        CityDto updatedCityDto = new CityDto();
        updatedCityDto.setName("Updated City");
        updatedCityDto.setPopulation(150000);
        updatedCityDto.setKnownFor("Famous for updates");
        updatedCityDto.setCountryId(1L);

        CityDto result = cityService.updateCity(1L, updatedCityDto);

        assertEquals("Updated City", result.getName());
        verify(cityRepo, times(1)).findById(1L);
        verify(countryRepo, times(1)).findById(1L);
        verify(cityRepo, times(1)).save(any(City.class));
    }

    @Test
    public void testDeleteCity() {
        doNothing().when(cityRepo).deleteById(anyLong());

        assertDoesNotThrow(() -> cityService.deleteCity(1L));
        verify(cityRepo, times(1)).deleteById(1L);
    }
}
