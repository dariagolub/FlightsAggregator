package com.travix.medusa.busyflights.domain.busyflights;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class BusyFlightsRequest {

    private final static String IATA_CODE_REGEXP = "[a-zA-Z]{3}";
    private final static String ISO_DATE_REGEXP = "[0-9]{4}-[0-9]{2}-[0-9]{2}";

    @Pattern(regexp = IATA_CODE_REGEXP)
    private String origin;
    @Pattern(regexp = IATA_CODE_REGEXP)
    private String destination;
    @Pattern(regexp = ISO_DATE_REGEXP)
    private String departureDate;
    @Pattern(regexp = ISO_DATE_REGEXP)
    private String returnDate;
    @Min(1)
    @Max(4)
    private int numberOfPassengers;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(final String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(final String destination) {
        this.destination = destination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(final String departureDate) {
        this.departureDate = departureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(final String returnDate) {
        this.returnDate = returnDate;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(final int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }
}
