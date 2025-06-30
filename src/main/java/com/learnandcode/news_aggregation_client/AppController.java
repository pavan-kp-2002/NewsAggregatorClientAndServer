package com.learnandcode.news_aggregation_client;

import com.learnandcode.news_aggregation_client.menu.MainMenu;
import com.learnandcode.news_aggregation_client.util.ScannerSingleton;

public class AppController {
    private final MainMenu mainMenu = new MainMenu();

    public void start() {
        while (true) {
            mainMenu.show();
        }
    }
}
