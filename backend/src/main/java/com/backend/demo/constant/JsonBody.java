package com.backend.demo.constant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class JsonBody {

    private final String timestamp;
    private final Integer statusCode;
    private final String error;
    private final String message;


    public JsonBody(Integer statusCode, String error, String message) {
        this.timestamp = createUCTTime();
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
    }

    private String createUCTTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date();
        return dateFormat.format(date);
    }
}
