package com.example.airport;

import java.util.Arrays;

public class StringModify {

    public static String padStart(String text, int length, char placeHolder) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() + text.length() < length) {
            sb.append(placeHolder);
        }
        sb.append(text);
        return sb.toString();
    }

    public static String padStart(Integer number, int length, char placeHolder) {
        return padStart(number.toString(), length, placeHolder);
    }
}
