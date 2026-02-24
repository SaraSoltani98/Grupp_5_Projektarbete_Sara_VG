HR-system – Grupp 5
Applikationens tema
Detta projekt är ett webbaserat HR-system utvecklat i Java med Spring Boot. Applikationen hanterar avdelningar,
anställda, roller och frånvaro genom separata CRUD-flöden. Systemet använder en molnbaserad PostgreSQL-databas.

Gruppmedlemmar
Johan
Kattis
Aslihan
Sara

Hur projektet startas
Projektet delas via GitHub. För att köra applikationen lokalt behöver man först klona repositoryt och öppna det i en
utvecklingsmiljö, t ex IntelliJ. Innan applikationen startas måste nödvändiga miljövariabler sättas (se nedan). Därefter
kan applikationen startas genom att köra Spring Boot-applikationen via IntelliJ eller genom Maven. När applikationen är
igång nås den via webbläsaren på http://localhost:8080.

Miljövariabler
Applikationen använder miljövariabler för att ansluta till den molnbaserade PostgreSQL-databasen. Följande variabler
krävs:
DB_URL – JDBC-adress till databasen
DB_USERNAME – Databasens användarnamn
DB_PASSWORD – Databasens lösenord
Dessa sätts lokalt på datorn innan applikationen startas och används i application.properties för att konfigurera
databaskopplingen.

## Sara VG-branch test

