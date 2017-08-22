package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by daryagolub on 22/08/17.
 */
public interface SupplierDataRetriever extends Callable<List<BusyFlightsResponse>> {

}
