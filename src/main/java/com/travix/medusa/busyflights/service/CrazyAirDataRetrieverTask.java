package com.travix.medusa.busyflights.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.utils.PriceUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by daryagolub on 22/08/17.
 *
 * 1. Converts request to suppliers format
 * 2. Make request to supplier
 * 3. Converts response to application format
 */

public class CrazyAirDataRetrieverTask implements SupplierDataRetriever {

    private BusyFlightsRequest busyFlightsRequest;

    CrazyAirDataRetrieverTask(BusyFlightsRequest busyFlightsRequest) {
        this.busyFlightsRequest = busyFlightsRequest;
    }

    @Override
    public List<BusyFlightsResponse> call() throws Exception {
        CrazyAirRequest crazyAirRequest = convertRequest(busyFlightsRequest);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity("http://localhost:8080/crazyair", new HttpEntity<>(crazyAirRequest), String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<CrazyAirResponse> flightsResponse = mapper.readValue(responseEntity.getBody(), mapper.getTypeFactory().constructCollectionType(List.class, CrazyAirResponse.class));
        return flightsResponse.stream().map(this::convertResponse).collect(Collectors.toList());
    }

    private CrazyAirRequest convertRequest(BusyFlightsRequest busyFlightsRequest) {
        CrazyAirRequest crazyAirRequest = new CrazyAirRequest();
        crazyAirRequest.setOrigin(busyFlightsRequest.getOrigin());
        crazyAirRequest.setDestination(busyFlightsRequest.getDestination());
        crazyAirRequest.setDepartureDate(busyFlightsRequest.getDepartureDate());
        crazyAirRequest.setReturnDate(busyFlightsRequest.getReturnDate());
        crazyAirRequest.setPassengerCount(busyFlightsRequest.getNumberOfPassengers());
        return crazyAirRequest;
    }

    private BusyFlightsResponse convertResponse(CrazyAirResponse crazyAirResponse) {
        BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
        busyFlightsResponse.setAirline(crazyAirResponse.getAirline());
        busyFlightsResponse.setSupplier("Crazy Air");
        busyFlightsResponse.setFare(PriceUtil.round(crazyAirResponse.getPrice()));
        busyFlightsResponse.setDepartureDate(crazyAirResponse.getDepartureDate());
        busyFlightsResponse.setArrivalDate(crazyAirResponse.getArrivalDate());
        busyFlightsResponse.setDepartureAirportCode(crazyAirResponse.getDepartureAirportCode());
        busyFlightsResponse.setDestinationAirportCode(crazyAirResponse.getDestinationAirportCode());
        return busyFlightsResponse;
    }
}
