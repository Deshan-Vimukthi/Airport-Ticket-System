package com.example.airport;

import java.util.Arrays;

public class StringModify {
    public static String padStart(String text,int length, char placeHolder){
        while (text.length()<length){
            char[] charArray = new char[text.length()+1];
            charArray[0] = placeHolder;
            text.getChars(0,text.length()-1,charArray,1);
            text = Arrays.toString(charArray);
        }
        return text;
    }
    public static String padStart(Integer text,int length, char placeHolder){
        return padStart(text.toString(),length,placeHolder);
    }
}
