package com.learnandcode.news_aggregation_client.menu;

import com.learnandcode.news_aggregation_client.service.NotificationService;
import com.learnandcode.news_aggregation_client.util.ScannerSingleton;
import com.learnandcode.news_aggregation_client.util.TokenStore;

public class NotificationsMenu implements Menu {
    private final MenuManager menuManager;
    private final NotificationService notificationService;

    public NotificationsMenu(MenuManager menuManager) {
        this.menuManager = menuManager;
        this.notificationService = new NotificationService();
    }

    @Override
    public void show() {
        System.out.println("N O T I F I C A T I O N S - M E N U");
        System.out.println("1. View Notifications");
        System.out.println("2. Configure Notifications");
        System.out.println("3. Back");
        System.out.println("4. Logout");
        String choice = ScannerSingleton.getInstance().nextLine();
        switch (choice) {
            case "1":
                notificationService.showNotifications();
                break;
            case "2":
                menuManager.navigateTo("CONFIGURE");
                break;
            case "3":
                menuManager.navigateTo("USER");
                return;
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
