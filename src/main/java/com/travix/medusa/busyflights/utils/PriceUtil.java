package com.travix.medusa.busyflights.utils;

import org.springframework.stereotype.Component;

/**
 * Created by daryagolub on 22/08/17.
 */

@Component
public class PriceUtil {

    public static double discountAndRound(double price, double discount) {
        double priceWithDiscount = price * (100 - discount) / 100;
        return round(priceWithDiscount);
    }

    public static double round(double price) {

        return Math.round(price * 100) / 100.0d;
    }
}
