package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

import java.util.List;

/**
 * Created by daryagolub on 22/08/17.
 */

public interface AggregateFlightsService {
    List<BusyFlightsResponse> aggregateFlights(BusyFlightsRequest request);
}
