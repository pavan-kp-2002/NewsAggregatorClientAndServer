package com.learnandcode.news_aggregation_client.menu;

import com.learnandcode.news_aggregation_client.util.ScannerSingleton;

public class AdminMenu {
    public void show() {
        System.out.println("Admin Menu:");
        System.out.println("1. View the list of external servers and status");
        System.out.println("2. View the external server's details");
        System.out.println("3.Update/Edit the external server's details");
        System.out.println("4. Add new News Category");
        System.out.println("5. Logout");

        String choice = ScannerSingleton.getInstance().nextLine();
        switch (choice){
            case "1":
                // Logic to view the list of external servers and status
                System.out.println("Viewing the list of external servers and status...");
                break;
            case "2":
                // Logic to view the external server's details
                System.out.println("Viewing the external server's details...");
                break;
            case "3":
                // Logic to update/edit the external server's details
                System.out.println("Updating/editing the external server's details...");
                break;
            case "4":
                // Logic to add a new News Category
                System.out.println("Adding a new News Category...");
                break;
            case "5":
                // Logic to logout
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice, please try again.");

        }
    }
}
