package com.mobileapp.utils;

import java.sql.Timestamp;

public class GetCurrentTime {
    public static void main(String[] args) {
        System.out.println(new Timestamp(System.currentTimeMillis()));
    }
}
