# Backend for Jexis

## User instructions

This project is a backend API, so use Postman to test the endpoints.

1. Open `application.yml` and set your local database credentials (`spring.datasource.*`) and JWT secrets (`jwt.secret.*`).
2. Make sure PostgreSQL is running and the database name from `application.yml` exists.
3. Start the app with:
   `./gradlew bootRun`
4. Once the app is running, open Postman and call the API at:
   `http://localhost:3000/api`
5. Use the available auth and business endpoints from Postman or from `http://localhost:3000/api/swagger-ui/index.html` to test the backend.

## Interfejs i integracja

Ta aplikacja jest przede wszystkim narzędziem do obsługi żądań HTTP wysyłanych za pomocą Postmana. Jej głównym celem jest uruchomienie backendu lokalnie i testowanie poszczególnych endpointów, aby sprawdzić, jak działa interfejs API oraz jak poszczególne moduły współpracują ze sobą. Dzięki takiemu podejściu można łatwo weryfikować logikę biznesową, autoryzację, integrację z bazą danych i poprawność odpowiedzi zwracanych przez aplikację.

<!-- TODO: include in project postman config file like in intern logger -->
