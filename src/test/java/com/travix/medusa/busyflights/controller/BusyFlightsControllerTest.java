package com.travix.medusa.busyflights.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.AggregateFlightsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by daryagolub on 22/08/17.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(BusyFlightsController.class)
public class BusyFlightsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AggregateFlightsService aggregateFlightsService;

    @Test
    public void searchFlightOk() throws Exception {
        BusyFlightsRequest busyFlightsRequest = new BusyFlightsRequest();
        busyFlightsRequest.setOrigin("LED");
        busyFlightsRequest.setDestination("AMS");
        busyFlightsRequest.setDepartureDate("2017-12-12");
        busyFlightsRequest.setReturnDate("2017-15-12");
        busyFlightsRequest.setNumberOfPassengers(2);

        BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
        busyFlightsResponse.setSupplier("Test");
        busyFlightsResponse.setDepartureAirportCode("TES");

        given(aggregateFlightsService.aggregateFlights(anyObject())).
                willReturn(Collections.singletonList(busyFlightsResponse));

        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(post("/searchFlights")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(busyFlightsRequest)))
                .andExpect((status().isOk()))
                .andExpect(content().contentType
                        (MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(mapper.writeValueAsString(Collections.singletonList(busyFlightsResponse))));
    }

    @Test
    public void searchFlightWrongRequest() throws Exception {
        BusyFlightsRequest busyFlightsRequest = new BusyFlightsRequest();
        busyFlightsRequest.setOrigin("LEDEL");
        busyFlightsRequest.setDestination("AMS");
        busyFlightsRequest.setDepartureDate("2017-12-12");
        busyFlightsRequest.setReturnDate("2017-15-12");
        busyFlightsRequest.setNumberOfPassengers(2);

        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(post("/searchFlights")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(busyFlightsRequest)))
                .andExpect((status().isBadRequest()));
    }


}
