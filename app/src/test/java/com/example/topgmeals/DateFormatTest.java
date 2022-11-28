package com.example.topgmeals;

import static org.junit.Assert.assertEquals;

import com.example.topgmeals.utils.DateFormat;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * This class tests the functionality of {@link DateFormat}
 */
public class DateFormatTest {
    private DateFormat dateFormat;

    @Test
    public void formatTest() throws ParseException {
        dateFormat = new DateFormat();
        String dateString = "12/31/1999";

        Date dateObj = dateFormat.toDate(dateString);
        assertEquals(dateString, dateFormat.parse(dateObj));
    }
}
