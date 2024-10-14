package com.cityatlas;

import com.cityatlas.dtos.CountryDto;
import com.cityatlas.mappers.CountryMapper;
import com.cityatlas.models.Continent;
import com.cityatlas.models.Country;
import com.cityatlas.repositories.CountryRepo;
import com.cityatlas.services.ContinentService;
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

    @Mock
    private ContinentService continentService;

    @InjectMocks
    private CountryService countryService;

    private Country country;
    private CountryDto countryDto;
    private Continent continent;

    @BeforeEach
    public void setup() {
        continent = new Continent(1L, "Europe");
        country = new Country(1L, "Sweden", "Swedish", 10000000L, continent);
        countryDto = new CountryDto(1L, "Sweden", "Swedish", 10000000L, continent.getId());
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
    void testGetCountryById() {
        // Arrange
        when(countryRepo.findById(1L)).thenReturn(Optional.of(country));
        when(countryMapper.toDto(country)).thenReturn(countryDto);

        // Act
        CountryDto result = countryService.getCountryById(1L);

        // Assert
        assertNotNull(result); // Verifiera att resultatet inte är null
        assertEquals(country.getName(), result.getName()); // Kontrollera att namnet stämmer
    }

    @Test
    void testAddCountry() {
        // Arrange
        when(continentService.getContinentById(countryDto.getContinentID())).thenReturn(continent);
        when(countryMapper.toEntity(countryDto)).thenReturn(country);
        when(countryRepo.save(any(Country.class))).thenReturn(country);
        when(countryMapper.toDto(country)).thenReturn(countryDto);

        // Act
        CountryDto result = countryService.addCountry(countryDto);

        // Assert
        assertNotNull(result); // Verifiera att resultatet inte är null
        assertEquals(country.getName(), result.getName()); // Kontrollera att namnet stämmer
    }

    @Test
    void testUpdateCountry() {
        // Arrange
        Country updatedCountry = new Country(1L, "Africa", "African", 50000000L, continent);
        CountryDto updatedCountryDto = new CountryDto(1L, "Africa", "African", 50000000L, continent.getId());

        when(countryRepo.findById(1L)).thenReturn(Optional.of(country));
        when(continentService.getContinentById(updatedCountryDto.getContinentID())).thenReturn(continent);
        when(countryMapper.toDto(any(Country.class))).thenReturn(updatedCountryDto);
        when(countryRepo.save(any(Country.class))).thenReturn(updatedCountry);

        // Act
        CountryDto result = countryService.updateCountry(1L, updatedCountryDto);

        // Assert
        assertNotNull(result); // Verifiera att resultatet inte är null
        assertEquals(updatedCountry.getName(), result.getName()); // Kontrollera att namnet uppdaterades korrekt

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
