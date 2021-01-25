package com.dailycode.user.management.encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderUtil {
    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public static void main(String[] args) {
        System.out.println(bCryptPasswordEncoder.encode("test123"));
    }
}