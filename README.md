# city-atlas

## Grupp 4

### Gruppens medlemmar:
- Samer Skayir
- Filip Björkman
- Mathias Bergman
- Isabella Lindquist
- Caroline Eklund
- Amanda Ek
- Andre Lilja

## Översikt
**city-atlas** är en kraftfull och användarvänlig plattform för att hämta, hantera och presentera information om städer och länder världen över. Detta API gör det möjligt för utvecklare att enkelt integrera geografisk data i sina applikationer och skapa engagerande användarupplevelser.

## Funktioner
- **Stadsinformation**: Hämta detaljerad information om olika städer, inklusive invånare, vad de är kända för och vilket land de tillhör.
- **Landinformation**: Få tillgång till information om länder, inklusive språk, invånare, huvudstad, världsdel och valuta.
- **Koppling mellan städer och länder**: Se hur städer är kopplade till sina respektive länder genom direkt åtkomst till den nödvändiga datan.
- **CRUD-operationer**: Genom API kan användare enkelt skapa, läsa, uppdatera och radera data om städer och länder, vilket ger maximal flexibilitet och kontroll.

## API-endpoints

### Länder
- **GET /user/countries** - Hämta en lista över alla länder.
- **POST /user/countries** - Lägg till ett nytt land.
- **PUT /admin/countries/{id}** - Uppdatera information om ett land.
- **DELETE /admin/countries/{id}** - Ta bort ett land.

### Städer
- **GET /user/cities** - Hämta en lista över alla städer.
- **POST /user/cities** - Lägg till en ny stad.
- **PUT /admin/cities/{id}** - Uppdatera information om en stad.
- **DELETE /admin/cities/{id}** - Ta bort en stad.

## Teknologier
CityAtlas API är byggt med moderna webbutvecklingstekniker, inklusive:
- **Spring Boot**: Används för backend-utveckling.
- **MySQL**: Används för datalagring.
- **GitHub Actions**: För kontinuerlig integration och testning.
- **JUnit**: För enhetstestning av API:t.

