package com.cityatlas;

import com.cityatlas.dtos.ContinentDto;
import com.cityatlas.models.Continent;
import com.cityatlas.repositories.ContinentRepo;
import com.cityatlas.services.ContinentService;
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
public class ContinentServiceTest {

    @Mock
    private ContinentRepo continentRepo;

    @InjectMocks
    private ContinentService continentService;

    private Continent continent;
    private ContinentDto continentDto;

    @BeforeEach
    public void setup() {
        this.continent = new Continent(1L, "Europe");
        this.continentDto = new ContinentDto(continent);
    }

    @Test
    void testGetAllContinents() {
        //Arrange
        when(continentRepo.findAll()).thenReturn(List.of(continent));

        //Act
        List<ContinentDto> result = continentService.getAllContinents();

        //Assert
        assertEquals(1, result.size());
        assertEquals(continent.getName(), result.get(0).getName());
    }

    @Test
    void testGetContinentById() {
        //Arrange
        when(continentRepo.findById(1L)).thenReturn(java.util.Optional.of(continent));

        //Act
        Continent result = continentService.getContinentById(1L);

        //Assert
        assertNotNull(result); // Verifiera att kontinenten inte är null
        assertEquals(continent.getName(), result.getName()); // Kolla att namnet stämmer
    }


    @Test
    void testAddContinent() {
        //Arrange
        Continent newContinent = new Continent(2L, "Asia"); // Skapa en ny kontinent för testet
        ContinentDto newContinentDto = new ContinentDto(newContinent); // Skapa en DTO för den nya kontinenten

        when(continentRepo.save(any(Continent.class))).thenReturn(newContinent); // Mock sparaoperation

        //Act
        ContinentDto result = continentService.addContinent(newContinentDto);

        //Assert
        assertNotNull(result);  // Verifiera att resultatet inte är null
        assertEquals(newContinent.getName(), result.getName());  // Verifiera att namnet är rätt
    }

    @Test
    void testUpdateContinent() {
        // Arrange
        Continent updatedContinent = new Continent(1L, "Africa"); // Skapa en uppdaterad kontinent
        ContinentDto updatedContinentDto = new ContinentDto(updatedContinent); // Skapa en DTO för uppdateringen

        when(continentRepo.findById(1L)).thenReturn(Optional.of(continent)); // Mock hitta operation
        when(continentRepo.save(any(Continent.class))).thenReturn(updatedContinent); // Mock spara operation

        // Act
        ContinentDto result = continentService.updateContinent(1L, updatedContinentDto);

        // Assert
        assertNotNull(result); // Verifiera att resultatet inte är null
        assertEquals(updatedContinent.getName(), result.getName()); // Kontrollera att namnet uppdaterades korrekt

        // Verify
        verify(continentRepo, times(1)).findById(1L);
        verify(continentRepo, times(1)).save(any(Continent.class));
    }

    @Test
    void testDeleteContinent() {
        //Arrange
        when(continentRepo.findById(1L)).thenReturn(java.util.Optional.of(continent));

        //Act
        continentService.deleteContinent(1L);

        //Verify (Som assert fastän det inte är en assert, används vid mockning)
        verify(continentRepo, times(1)).deleteById(1L);
    }
}
