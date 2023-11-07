package com.mobileapp;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class Test {
    public static void main(String[] args) throws ParseException {
        Timestamp timestamp=new Timestamp(new Date().getTime());
        System.out.println(timestamp.getTime());
    }
}
