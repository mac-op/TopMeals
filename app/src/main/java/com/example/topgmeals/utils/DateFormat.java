package com.example.topgmeals.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Helper Object to format Date into MM/dd/yyyy
 */
public class DateFormat {
    private final String format = "MM/dd/yyyy";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CANADA);

    public String parse(Date date){
        return dateFormat.format(date);
    }

    public Date toDate(String date) throws ParseException {
        return dateFormat.parse(date);
    }
}
