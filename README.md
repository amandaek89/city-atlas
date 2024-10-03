# city-atlas

## Grupp 4

### Gruppens medlemmar:
- Samer Skayir
- Filip Björkman
- Mathias Bergman
- Isabella Lindquist
- Caroline Eklund
- Amanda Ek

## Översikt
**City-atlas** är ett API för att hämta, hantera och presentera information om städer, länder och världsdelar. 
Detta API gör det möjligt för utvecklare att enkelt integrera geografisk data i sina applikationer och skapa engagerande användarupplevelser.

## Funktioner
- **Stadsinformation**: Hämta detaljerad information om olika städer, inklusive invånare, vad de är kända för och vilket land de tillhör.
- **Landinformation**: Få tillgång till information om länder, inklusive språk, invånare, huvudstad och världsdel de tillhör.
- **Världsdelar**: Se vilka hur många invånare de har, hur stor yta samt vilka länder som tillhör de olika världsdelarna.
- **CRUD-operationer**: Genom API kan användare enkelt skapa, läsa, uppdatera och radera data om städer, länder och världsdelar. 

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

### Världsdelar
- **GET /user/continents** - Hämta en lista över alla världsdelar.
- **POST /user/continents** - Lägg till en ny världsdel.
- **PUT /admin/continents/{id}** - Uppdatera information om en världsdel.
- **DELETE /admin/continents/{id}** - Ta bort en världsdel.

## Teknologier
CityAtlas API är byggt med moderna webbutvecklingstekniker, inklusive:
- **Spring Boot**: Används för backend-utveckling.
- **MySQL**: Används för datalagring.
- **GitHub Actions**: För kontinuerlig integration och testning.
- **JUnit**: För enhetstestning av API:t.
- **Swagger**: För dokumentation av API-endpoints.

