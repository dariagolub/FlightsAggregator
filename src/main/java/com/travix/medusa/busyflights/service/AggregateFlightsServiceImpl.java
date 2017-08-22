package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by daryagolub on 22/08/17.
 *
 * Executes tasks for fetching data from suppliers in parallel and aggregate received data
 */

@Service
public class AggregateFlightsServiceImpl implements AggregateFlightsService {

    private final static int POOL_SIZE = 2;
    private final static Logger LOGGER = LoggerFactory.getLogger(AggregateFlightsServiceImpl.class);

    @Override
    public List<BusyFlightsResponse> aggregateFlights(BusyFlightsRequest request) {
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        List<Callable<List<BusyFlightsResponse>>> tasks =
                Arrays.asList(new CrazyAirDataRetrieverTask(request), new ToughJetDataRetrieverTask(request));

        List<Future<List<BusyFlightsResponse>>> results;
        List<BusyFlightsResponse> flights = new ArrayList<>();
        try {
            results = pool.invokeAll(tasks);
            for (Future<List<BusyFlightsResponse>> future : results) {
                flights.addAll(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Error during tasks execution", e);
        }

        pool.shutdown();

        flights.sort((Comparator.comparingDouble(BusyFlightsResponse::getFare)));
        return flights;
    }
}
