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
        ContinentDto result = continentService.getContinentById(1L);

        //Assert
        assertEquals(continent.getName(), result.getName());
    }

    @Test
    void testAddContinent() {
        //Arrange
        when(continentRepo.save(continentDto.toEntity())).thenReturn(continent);

        //Act
        ContinentDto result = continentService.addContinent(continentDto);

        //Assert
        assertEquals(continent.getName(), result.getName());
    }

    @Test
    void testUpdateContinent() {
        // Arrange
        when(continentRepo.findById(1L)).thenReturn(Optional.of(continent));

        // Act
        ContinentDto result = continentService.updateContinent(1L, continentDto);

        // Assert
        assertNotNull(result);
        assertEquals("Africa", result.getName());

        // Verify (Som assert fastän det inte är en assert, används vid mockning)
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
