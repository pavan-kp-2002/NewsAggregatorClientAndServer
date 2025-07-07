package com.learnandcode.news_aggregation_client;

import com.learnandcode.news_aggregation_client.menu.*;
import com.learnandcode.news_aggregation_client.util.ScannerSingleton;

public class AppController {
    private final MenuManager menuManager = new MenuManager();

    public void start() {
        menuManager.register("MAIN", new MainMenu(menuManager));
        menuManager.register("USER", new UserMenu(menuManager));
        menuManager.register("ADMIN", new AdminMenu(menuManager));
        menuManager.register("HEADLINES", new HeadlinesOptionMenu(menuManager));
        menuManager.register("CONFIGURE", new ConfigurationMenu(menuManager));
        menuManager.register("NOTIFICATIONS", new NotificationsMenu(menuManager));
        menuManager.register("KEYWORD-CONFIG", new KeywordConfigurationMenu(menuManager));
        menuManager.register("CATEGORY-CONFIG", new CategoryConfigurationMenu(menuManager));
        menuManager.register("MODERATION", new ModerationMenu(menuManager));
        menuManager.navigateTo("MAIN");
    }
}
