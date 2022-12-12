# flight-cache
Flight data REST API built in Spring Boot

## Endpoints
This application exposes a wide range of json rest endpoints suitable for any application

> Application base URL: `http://82.208.22.205:7000` or `https://flight.pequla.com`

- GET `/api/flight` - Paged response of future flights 
- GET `/api/flight/all` - Paged response of all flights
- GET `/api/flight/{id}` - Flight for ID
- GET `/api/flight/destination` - List of all destinations (suitable for in application dropdowns)
- GET `/api/flight/destination/{dest}` - Paged response of all future flights to that destination