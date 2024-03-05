# flight-cache
Flight data REST API built in Spring Boot

## Endpoints
This application exposes a wide range of json rest endpoints suitable for any application

> Application base URL: `https://flight.pequla.com`

- GET `/api/flight` - Paged response of future flights
- GET `/api/flight/list` - List of all future flights
- GET `/api/flight/all` - Paged response of all flights
- GET `/api/flight/{id}` - Flight for ID
- POST `/api/flight/list` - Flights for IDs sent in request body as array
- GET `/api/flight/destination` - List of destinations of all future flights
- GET `/api/flight/destination/search/{dest}` - List of autocompleted destinations for future flights
- GET `/api/flight/destination/all` - List of all destinations
- GET `/api/flight/destination/all/search/{dest}` - List of autocompleted destinations for all flights
- GET `/api/flight/destination/{dest}` - Paged response of all future flights to that destination

> Endpoints returning list of destinations are suitable for in app dropdowns, where endpoints with `/search` are suitable for autocomplete inputs

> You can use query param **?type=** (arrival or departure) on endpoints that return flight objects to get **desired flights by type** 
