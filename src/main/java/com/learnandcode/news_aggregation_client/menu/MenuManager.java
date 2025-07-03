package com.learnandcode.news_aggregation_client.menu;

import java.util.HashMap;
import java.util.Map;

public class MenuManager {
    private final Map<String, Menu> menuMap = new HashMap<>();

    public void register(String key, Menu menu) {
        menuMap.put(key, menu);
    }

    public void navigateTo(String key) {
        Menu menu = menuMap.get(key.toUpperCase());
        if (menu != null) {
            menu.show();
        } else {
            System.out.println("Menu '" + key + "' not found.");
        }
    }
}
