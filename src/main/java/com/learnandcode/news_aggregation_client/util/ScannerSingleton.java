package com.learnandcode.news_aggregation_client.util;

import java.util.Scanner;

public class ScannerSingleton {
    private static Scanner instance = new Scanner(System.in);

    private ScannerSingleton() {}

    public static Scanner getInstance() {
        return instance;
    }
}
