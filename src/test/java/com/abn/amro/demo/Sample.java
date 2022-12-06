package com.abn.amro.demo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class Sample {


    public static void main(String[] args) {

        String s1 = new String("ababa");

        StringBuffer s2 = new StringBuffer(s1);

        s2.reverse();

        if (s1.equals(s2.toString())) {
            System.out.println("String is pallindrom");
        }

    }

}