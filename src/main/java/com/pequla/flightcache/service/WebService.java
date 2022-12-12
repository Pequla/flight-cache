package com.pequla.flightcache.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.pequla.flightcache.model.FlightModel;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WebService {

    private final HttpClient client;
    private final XmlMapper mapper;

    private static final String API_URL = "https://beg.aero/sites/belgrade/files/data/redLetenja.xml";

    public WebService() {
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(3))
                .build();

        // Register xml mapper
        mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public FlightModel[] getFlightsFromBelgrade() throws IOException, InterruptedException {
        // Retrieve flight data
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .build();
        HttpResponse<String> rsp = client.send(req, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(rsp.body(), FlightModel[].class);
    }

    public List<FlightModel> getCurrentDay() throws IOException, InterruptedException {
        return Arrays.stream(getFlightsFromBelgrade()).filter(flight -> Objects.equals(flight.getDAN(), "0")).collect(Collectors.toList());
    }
}
