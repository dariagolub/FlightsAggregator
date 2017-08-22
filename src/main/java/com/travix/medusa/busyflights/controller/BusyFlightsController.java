package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.AggregateFlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by daryagolub on 22/08/17.
 *
 * Controller for end-points of busyFlights application
 */

@RestController
public class BusyFlightsController {


    @Autowired
    private AggregateFlightsService aggregateFlightsService;

    @RequestMapping(value = "/searchFlights", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    List<BusyFlightsResponse> searchFlight(@Valid @RequestBody BusyFlightsRequest request) {
        return aggregateFlightsService.aggregateFlights(request);
    }
}
