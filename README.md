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

## Inloggningsfunktioner och Rollbaserad Accesskontroll
Applikationen använder **Spring Security** i kombination med **JWT** (JSON Web Tokens) för autentisering och autorisering. Vid uppstart av applikationen skapas en **admin**-användare med fördefinierade inloggningsuppgifter.

### Standardadmin vid start
Vid uppstart skapas automatiskt en användare med följande uppgifter:
- **Användarnamn**: `admin`
- **Lösenord**: `admin`
- **Roll**: `ADMIN`

Denna användare har fulla behörigheter och kan lägga till eller hantera andra användare i systemet.

## Användarroller
Det finns en lista för `user_roles` som automatiskt tilldelar rollen `USER` när en användare skapas. Endast en **ADMIN**-användare har behörighet att ändra roller och kan ge en befintlig användare rollen `ADMIN`. Detta säkerställer att endast auktoriserade användare kan få högre behörigheter och bidrar till ökad säkerhet i applikationen.

## Sammanfattning av Behörigheter

| Roll     | GET | POST | PUT | DELETE | Skapa Användare | Uppdatera Roll |
|----------|-----|------|-----|--------|-----------------|----------------|
| USER     | ✅  | ✅   | ❌  | ❌     | ❌               | ❌              |
| ADMIN    | ✅  | ✅   | ✅  | ✅     | ✅               | ✅              |

## Funktioner
- **Stadsinformation**: Hämta detaljerad information om olika städer, inklusive invånare, vad de är kända för och vilket land de tillhör.
- **Landinformation**: Få tillgång till information om länder, inklusive språk, invånare, huvudstad och världsdel de tillhör.
- **Världsdelar**: Se vilka hur många invånare de har, hur stor yta samt vilka länder som tillhör de olika världsdelarna.
- **CRUD-operationer**: Genom API kan användare effektivt skapa, läsa, uppdatera och radera data om städer, länder och världsdelar.

## Relationer mellan entiteter
Städer, länder och världsdelar är kopplade till varandra genom en hierarkisk struktur. När en användare raderar en överordnad entitet (t.ex. ett land), raderas även alla underordnade entiteter (t.ex. städer) som är kopplade till den. Detta beteende kallas **cascade delete**, och det säkerställer att data är konsistent och att inga överflödiga poster blir kvar i systemet.

## API-endpoints

### User management
- **GET /admin/user** - Hämta en lista över alla användare.
- **PUT /admin/user** - Uppdatera lösenord för en användare.
- **PUT /admin/user/roles** - Tilldela roller till en användare.
- **GET /admin/user/{username}** - Hämta information om en specifik användare.
- **DELETE /admin/user/{username}** - Ta bort en användare.

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

### Säkerhetskonfigurationer
- **Spring Security** används för att hantera säkerheten i applikationen. Det kontrollerar autentisering och auktorisering baserat på användarens roll (USER eller ADMIN).
- **JWT (JSON Web Tokens)** används för att verifiera användares identitet och behörigheter. När en användare loggar in får de en JWT-token som används för att autentisera deras begäran.
- **PasswordEncoder**: Lösenord för alla användare krypteras vid skapande och lagras aldrig i klartext.
- **CORS-konfiguration** är inställd för att tillåta begäran från betrodda domäner, vilket möjliggör att front-end och back-end kan kommunicera säkert över olika ursprung.

## Teknologier
CityAtlas API är byggt med moderna webbutvecklingstekniker, inklusive:
- **Spring Boot**: Används för backend-utveckling.
- **MySQL**: Används för datalagring.
- **GitHub Actions**: För kontinuerlig integration och testning.
- **JUnit**: För enhetstestning av API:t.
- **Swagger**: För dokumentation av API-endpoints.
- **AWS**: Används för molnhosting och infrastrukturhantering:
    - **RDS (Relational Database Service)**: För att hantera och skala MySQL-databasen.
    - **Elastic Beanstalk**: För att automatisera distribution och skalning av applikationen.
    - **CodeBuild**: För att automatisera byggprocesser och testning.
    - **CodePipeline**: För att skapa en CI/CD-pipeline som automatiserar byggning, testning och distribution.
