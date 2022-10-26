package com.example.topgmeals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Object to format Date into MM/dd/yyyy
 */
public class DateFormat {
    private String format = "MM/dd/yyyy";
    private SimpleDateFormat dateFormat=new SimpleDateFormat(format, Locale.CANADA);

    public String parse(Date date){
        return dateFormat.format(date);
    }
}
