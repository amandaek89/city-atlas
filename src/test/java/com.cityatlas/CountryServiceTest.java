package com.cityatlas;

import com.cityatlas.dtos.CountryDto;
import com.cityatlas.mappers.CountryMapper;
import com.cityatlas.models.Country;
import com.cityatlas.repositories.CountryRepo;
import com.cityatlas.services.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryServiceTest {

    @Mock
    private CountryRepo countryRepo;

    @Mock
    private CountryMapper countryMapper;

    @InjectMocks
    private CountryService countryService;

    private Country country;
    private CountryDto countryDto;

    @BeforeEach
    public void setup() {
        this.country = new Country(1L, "Sweden", "Swedish", 10000000L, null);
        this.countryDto = new CountryDto(1L, "Sweden", "Swedish", 10000000L, null);
    }

    @Test
    void testGetAllCountries() {
        // Arrange
        when(countryRepo.findAll()).thenReturn(List.of(country));
        when(countryMapper.toDto(country)).thenReturn(countryDto);

        // Act
        List<CountryDto> result = countryService.getAllCountries();

        // Assert
        assertEquals(1, result.size());
        assertEquals(country.getName(), result.get(0).getName());
    }

    @Test
    void testAddCountry() {
        // Arrange
        when(countryMapper.toEntity(countryDto)).thenReturn(country); // Använd den nya metoden utan continent
        when(countryRepo.save(country)).thenReturn(country);
        when(countryMapper.toDto(country)).thenReturn(countryDto);

        // Act
        CountryDto result = countryService.addCountry(countryDto); // Ingen continent behövs

        // Assert
        assertEquals(country.getName(), result.getName());
    }


    @Test
    void testUpdateCountry() {
        // Arrange
        when(countryRepo.findById(1L)).thenReturn(Optional.of(country));
        when(countryMapper.toDto(any(Country.class))).thenReturn(new CountryDto(1L, "Norway", "Norwegian", 5000000L, null));

        // Act
        CountryDto result = countryService.updateCountry(1L, countryDto);

        // Assert
        assertNotNull(result);
        assertEquals("Norway", result.getName());

        // Verify
        verify(countryRepo, times(1)).findById(1L);
        verify(countryRepo, times(1)).save(any(Country.class));
    }

    @Test
    void testDeleteCountry() {
        // Arrange
        when(countryRepo.findById(1L)).thenReturn(Optional.of(country));

        // Act
        countryService.deleteCountry(1L);

        // Verify
        verify(countryRepo, times(1)).deleteById(1L);
    }
}
