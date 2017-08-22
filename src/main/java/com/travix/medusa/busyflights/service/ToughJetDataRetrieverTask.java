package com.travix.medusa.busyflights.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.utils.PriceUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by daryagolub on 22/08/17.
 */

public class ToughJetDataRetrieverTask implements SupplierDataRetriever {

    private BusyFlightsRequest busyFlightsRequest;

    ToughJetDataRetrieverTask(BusyFlightsRequest busyFlightsRequest) {
        this.busyFlightsRequest = busyFlightsRequest;
    }

    @Override
    public List<BusyFlightsResponse> call() throws Exception {
        ToughJetRequest toughJetRequest = convertRequest(busyFlightsRequest);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity("http://localhost:8080/toughjet", new HttpEntity<>(toughJetRequest), String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<ToughJetResponse> flightsResponse = mapper.readValue(responseEntity.getBody(), mapper.getTypeFactory().constructCollectionType(List.class, ToughJetResponse.class));
        return flightsResponse.stream().map(this::convertResponse).collect(Collectors.toList());
    }

    private ToughJetRequest convertRequest(BusyFlightsRequest busyFlightsRequest) {
        ToughJetRequest toughJetRequest = new ToughJetRequest();
        toughJetRequest.setFrom(busyFlightsRequest.getOrigin());
        toughJetRequest.setTo(busyFlightsRequest.getDestination());
        toughJetRequest.setOutboundDate(busyFlightsRequest.getDepartureDate());
        toughJetRequest.setInboundDate(busyFlightsRequest.getReturnDate());
        toughJetRequest.setNumberOfAdults(busyFlightsRequest.getNumberOfPassengers());
        return toughJetRequest;
    }

    private BusyFlightsResponse convertResponse(ToughJetResponse toughJetResponse) {
        BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
        busyFlightsResponse.setAirline(toughJetResponse.getCarrier());
        busyFlightsResponse.setSupplier("Tough Jet");
        busyFlightsResponse.setFare(PriceUtil.discountAndRound(toughJetResponse.getBasePrice(), toughJetResponse.getDiscount()));
        busyFlightsResponse.setDepartureDate(toughJetResponse.getOutboundDateTime());
        busyFlightsResponse.setArrivalDate(toughJetResponse.getInboundDateTime());
        busyFlightsResponse.setDepartureAirportCode(toughJetResponse.getDepartureAirportName());
        busyFlightsResponse.setDestinationAirportCode(toughJetResponse.getArrivalAirportName());
        return busyFlightsResponse;
    }
}
