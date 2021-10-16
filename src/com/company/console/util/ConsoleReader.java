package com.company.console.util;

import java.util.Scanner;

public class ConsoleReader {

    public static String readString(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static int readInt(){
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
}
