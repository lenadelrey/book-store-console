package com.company.console.util;

public class ConsoleWriter {

    public static void writeString(String message) {
        System.out.println(message);
    }

    public static void writeStrings(String[] messages) {
        for (String message : messages) {
            System.out.println(message);
        }
    }

    public static void writeObject(Object object) {
        System.out.println(object.toString());
    }

    public static void writeObjects(Object[] objects) {
        for (Object object : objects) {
            System.out.println(object.toString());
        }
    }

    public static void writeInt(int message) {
        System.out.println(message);
    }
}
