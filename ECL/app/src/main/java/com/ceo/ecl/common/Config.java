package com.ceo.ecl.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Config {

    public static String getFormattedDateSimple(Long dateTime) {
        SimpleDateFormat newFormat = new SimpleDateFormat("MMMM dd, yyyy");
        return newFormat.format(new Date(dateTime));
    }
}
