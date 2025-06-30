package com.learnandcode.news_aggregation_client.menu;
import com.learnandcode.news_aggregation_client.util.ScannerSingleton;
import com.learnandcode.news_aggregation_client.util.TokenStore;

public class UserMenu {
    private final MainMenu mainMenu = new MainMenu();
    private final HeadlinesOptionMenu headlinesOptionMenu = new  HeadlinesOptionMenu();

    public void show() {
        System.out.println("Welcome to the News Application!");
        System.out.println("Please choose the options below:");
        System.out.println("1. Headlines");
        System.out.println("2. Saved Articles");
        System.out.println("3. Search");
        System.out.println("4. Notifications");
        System.out.println("5. Logout");
        String choice = ScannerSingleton.getInstance().nextLine();
        switch (choice) {
            case "1":
                headlinesOptionMenu.show();
                break;
            case "2":
                // Show saved articles
                break;
            case "3":
                // Show search outputs
                break;
            case "4":
                // Show notifications
                break;
            case "5":
                System.out.println("Logging out...");
                TokenStore.clear();
                mainMenu.show();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                show();
        }
    }
}
