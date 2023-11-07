package com.mobileapp.utils;

import java.security.MessageDigest;

public class EncryptPassword {
    public String encryptPassword(String password){
        StringBuilder encodePass = new StringBuilder();
        try {
            MessageDigest messageDigest=MessageDigest.getInstance("SHA-256");
            byte[] bytePass=password.getBytes("UTF-8");
            messageDigest.update(bytePass);
            byte[] hashBytes = messageDigest.digest();

            for (byte b : hashBytes) {
                String hex = String.format("%02x", b);
                encodePass.append(hex);
            }
            System.out.println(encodePass);
        }catch (Exception e){
            System.out.println("encrypt pass error "+ e.getMessage());
        }
        return new String(encodePass);
    }
}
