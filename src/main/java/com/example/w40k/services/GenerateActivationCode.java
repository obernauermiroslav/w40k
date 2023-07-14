package com.example.w40k.services;

import java.util.Random;

public class GenerateActivationCode {
    private static final int LEFT_LIMIT = 48;
    private static final int RIGHT_LIMIT = 122;

    public static String generate(int length) {
        Random random = new Random();
        String randomCode = random.ints(LEFT_LIMIT, RIGHT_LIMIT + 1)
                .filter(i -> (i <=57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return randomCode;
    }
}
