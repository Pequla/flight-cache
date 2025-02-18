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

## Examples

Here are a few of the example requests

- `https://flight.pequla.com/api/flight?page=0&size=3&type=departure&sort=scheduledAt,desc`
```json
{
    "content": [
        {
            "id": 168772,
            "type": {
                "id": 2,
                "name": "DEPARTURE"
            },
            "flightKey": "202502200945IDVF  5080VF  508",
            "flightNumber": "VF  508",
            "destination": "Ankara - Esenboga",
            "scheduledAt": "2025-02-20T09:45:00",
            "estimatedAt": null,
            "connectedType": "0",
            "connectedFlight": null,
            "plane": "7M8",
            "gate": null,
            "terminal": "2"
        },
        {
            "id": 168776,
            "type": {
                "id": 2,
                "name": "DEPARTURE"
            },
            "flightKey": "202502200945IDJU  6001JU  600",
            "flightNumber": "JU  600",
            "destination": "Zagreb",
            "scheduledAt": "2025-02-20T09:45:00",
            "estimatedAt": null,
            "connectedType": "1",
            "connectedFlight": "JU  600",
            "plane": "AT7",
            "gate": null,
            "terminal": "2"
        },
        {
            "id": 168777,
            "type": {
                "id": 2,
                "name": "DEPARTURE"
            },
            "flightKey": "202502200905IDRO  2121RO  212",
            "flightNumber": "RO  212",
            "destination": "Bucharest",
            "scheduledAt": "2025-02-20T09:05:00",
            "estimatedAt": null,
            "connectedType": "1",
            "connectedFlight": "RO  212",
            "plane": "AT7",
            "gate": null,
            "terminal": "2"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": true,
            "empty": false,
            "unsorted": false
        },
        "pageNumber": 0,
        "pageSize": 3,
        "offset": 0,
        "unpaged": false,
        "paged": true
    },
    "totalPages": 49,
    "totalElements": 145,
    "last": false,
    "size": 3,
    "number": 0,
    "sort": {
        "sorted": true,
        "empty": false,
        "unsorted": false
    },
    "numberOfElements": 3,
    "first": true,
    "empty": false
}
```

- `https://flight.pequla.com/api/flight/168772`
```json
{
    "id": 168772,
    "type": {
        "id": 2,
        "name": "DEPARTURE"
    },
    "flightKey": "202502200945IDVF  5080VF  508",
    "flightNumber": "VF  508",
    "destination": "Ankara - Esenboga",
    "scheduledAt": "2025-02-20T09:45:00",
    "estimatedAt": null,
    "connectedType": "0",
    "connectedFlight": null,
    "plane": "7M8",
    "gate": null,
    "terminal": "2"
}
```
