package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by daryagolub on 22/08/17.
 */

@RestController
public class SuppliersController {

    @RequestMapping(value = "/crazyair", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    List<CrazyAirResponse> getCrazyAirFlights(@RequestBody CrazyAirRequest request) {
        CrazyAirResponse flight1 = new CrazyAirResponse();
        flight1.setAirline("KLM");
        flight1.setPrice(123.0034);
        flight1.setCabinclass("B");
        flight1.setDepartureAirportCode("AMS");
        flight1.setDestinationAirportCode("LED");
        flight1.setDepartureDate("2017-09-03T07:15:30");
        flight1.setArrivalDate("2017-09-03T10:15:30");

        CrazyAirResponse flight2 = new CrazyAirResponse();
        flight2.setAirline("Airbaltic");
        flight2.setPrice(435.98);
        flight2.setCabinclass("B");
        flight2.setDepartureAirportCode("AMS");
        flight2.setDestinationAirportCode("LED");
        flight2.setDepartureDate("2017-09-03T12:16:30");
        flight2.setArrivalDate("2017-09-03T15:16:30");

        return Arrays.asList(flight1, flight2);
    }

    @RequestMapping(value = "/toughjet", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    List<ToughJetResponse> getToughJetFlights(@RequestBody ToughJetRequest request) {
        ToughJetResponse flight1 = new ToughJetResponse();
        flight1.setCarrier("S7");
        flight1.setBasePrice(345);
        flight1.setTax(10.00);
        flight1.setDiscount(15);
        flight1.setDepartureAirportName("AMS");
        flight1.setArrivalAirportName("LED");
        flight1.setOutboundDateTime("2017-09-03T04:15:30");
        flight1.setInboundDateTime("2017-09-03T07:15:30");

        ToughJetResponse flight2 = new ToughJetResponse();
        flight2.setCarrier("KLM");
        flight2.setBasePrice(477);
        flight2.setTax(10.00);
        flight2.setDiscount(0);
        flight2.setDepartureAirportName("AMS");
        flight2.setArrivalAirportName("LED");
        flight2.setOutboundDateTime("2017-09-03T10:15:30");
        flight2.setInboundDateTime("2017-09-03T13:15:30");

        return Arrays.asList(flight1, flight2);
    }
}
