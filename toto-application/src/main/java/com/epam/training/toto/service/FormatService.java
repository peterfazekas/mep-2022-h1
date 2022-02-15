package com.epam.training.toto.service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatService {

    private static final Locale LOCALE_HU = new Locale("hu", "HU");
    private static final DecimalFormat decimalFormat;
    private static final NumberFormat currencyFormat;

    static  {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator('.');
        decimalFormat = new DecimalFormat("###,###.##", otherSymbols);
        currencyFormat = NumberFormat.getCurrencyInstance(LOCALE_HU);
        currencyFormat.setMaximumFractionDigits(0);
    }

    public static <T> String formattedCurrency(T amount) {
        return currencyFormat.format(amount);
    }

    public static <T> String formattedNumber(T amount) {
        return decimalFormat.format(amount);
    }


}
