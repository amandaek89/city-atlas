package com.cityatlas.mappers;

import com.cityatlas.dtos.CountryDto;
import com.cityatlas.models.Continent;
import com.cityatlas.models.Country;
import org.springframework.stereotype.Component;

/**
 * Mapper-klass för att konvertera mellan Country entity och CountryDto.
 * Används för att omvandla data mellan olika lager i applikationen.
 */
@Component
public class CountryMapper {

    /**
     * Konverterar en Country entity till ett CountryDto.
     *
     * @param country den Country entity som ska konverteras
     * @return ett CountryDto med motsvarande information
     */
    public CountryDto toDto(Country country) {
        return new CountryDto(
                country.getId(),
                country.getName(),
                country.getLanguage(),
                country.getPopulation(),
                country.getContinent() != null ? country.getContinent().getId() : null //hämtar id för Continent om den finns
        );
    }

    /**
     * Konverterar ett CountryDto till en Country entity utan continent.
     * Används vid skapande av ett nytt land.
     *
     * @param countryDto det CountryDto som ska konverteras
     * @return en ny Country entity utan continent
     */
    public Country toEntity(CountryDto countryDto) {
        return new Country(
                countryDto.getName(),
                countryDto.getLanguage(),
                countryDto.getPopulation(),
                null //ingen continent vid skapande
        );
    }

    /**
     * Konverterar ett CountryDto till en Country entity med continent.
     * Används vid uppdatering av ett befintligt land.
     *
     * @param countryDto det CountryDto som ska konverteras
     * @param continent  den continent som ska kopplas som parent
     * @return en ny Country entity med angiven continent
     */
    public Country toEntity(CountryDto countryDto, Continent continent) {
        return new Country(
                countryDto.getName(),
                countryDto.getLanguage(),
                countryDto.getPopulation(),
                continent // continent som parent
        );
    }

    /**
     * Konverterar ett CountryDto med id till en Country entity.
     * Används vid uppdatering av ett befintligt land.
     *
     * @param id          ID för det land som ska uppdateras
     * @param countryDto  det CountryDto som ska konverteras
     * @param continent   den continent som ska kopplas som parent
     * @return en ny Country entity med angiven ID och continent
     */
    public Country toEntityWithId(Long id, CountryDto countryDto, Continent continent) {
        return new Country(
                id,
                countryDto.getName(),
                countryDto.getLanguage(),
                countryDto.getPopulation(),
                continent // continent som parent
        );
    }
}
