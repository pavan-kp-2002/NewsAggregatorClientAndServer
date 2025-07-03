package com.learnandcode.news_aggregation_client.menu;

import com.learnandcode.news_aggregation_client.util.ScannerSingleton;
import com.learnandcode.news_aggregation_client.util.TokenStore;

public class ConfigurationMenu implements Menu{
    private final MenuManager menuManager;

    public ConfigurationMenu(MenuManager menuManager) {
        this.menuManager = menuManager;
    }
    @Override
    public void show() {
        System.out.println("C O N F I G U R E - N O T I F I C A T I O N S");
        System.out.println("1. Category Configuration");
        System.out.println("2. Keyword Configuration");
        System.out.println("3. Back");
        System.out.println("4. Logout");

        System.out.println("Enter Your Option: ");
        String configChoice = ScannerSingleton.getInstance().nextLine();
        switch (configChoice) {
            case "1":
                System.out.println("Category configuration opened.");
                break;
            case "2":
                System.out.println("Keyword configuration opened.");
                break;
            case "3":
                menuManager.navigateTo("NOTIFICATIONS");
                break;
            case "4":
                TokenStore.clear();
                menuManager.navigateTo("MAIN");
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        show();
    }
}
