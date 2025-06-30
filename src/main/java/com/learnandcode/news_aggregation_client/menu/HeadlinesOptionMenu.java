package com.learnandcode.news_aggregation_client.menu;

import com.learnandcode.news_aggregation_client.util.ScannerSingleton;

public class HeadlinesOptionMenu {
    public void show(){
        System.out.println("Please choose the options below:");
        System.out.println("1. Today");
        System.out.println("2. Date range");
        System.out.println("3. Log0ut");

        String choice = ScannerSingleton.getInstance().nextLine();
        switch (choice){
            case "1":
                System.out.println("Fetching today's headlines...");
                break;
            case "2":
                System.out.println("Please enter the start date (DD-MM-YYYY):");
                String startDate = ScannerSingleton.getInstance().nextLine();
                System.out.println("Please enter the end date (DD-MM-YYYY):");
                String endDate = ScannerSingleton.getInstance().nextLine();
                System.out.println("Fetching headlines from " + startDate + " to " + endDate + "...");
                break;
            case "3":
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }
}
