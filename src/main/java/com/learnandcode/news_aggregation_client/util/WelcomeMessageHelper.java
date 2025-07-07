package com.learnandcode.news_aggregation_client.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WelcomeMessageHelper {
    public static void printWelcomeMessage(){
        String userName = TokenStore.getUserName();
        String formattedUserName = userName.substring(0,1).toUpperCase() + userName.substring(1).toLowerCase();
        String welcomeMsg = "Welcome to the News Aggregation Application, " + formattedUserName+ "!";

        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy"));
        String time = now.format(DateTimeFormatter.ofPattern("hh:mm a"));
        String currentDateAndTime = "Date: " + date + " Time: " + time;

        System.out.println(welcomeMsg);
        System.out.println(currentDateAndTime);

        int dotCount = welcomeMsg.replace("\n", "").length();
        for (int i = 0; i < dotCount; i++) {
            System.out.print(".");
        }
        System.out.println();
    }
}
