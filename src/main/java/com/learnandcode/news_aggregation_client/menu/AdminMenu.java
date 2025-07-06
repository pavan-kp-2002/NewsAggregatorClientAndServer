package com.learnandcode.news_aggregation_client.menu;

import com.learnandcode.news_aggregation_client.service.AdminService;
import com.learnandcode.news_aggregation_client.util.ScannerSingleton;
import com.learnandcode.news_aggregation_client.util.WelcomeMessageHelper;

public class AdminMenu implements Menu {
    private final AdminService adminService = new AdminService();
    private final MenuManager menuManager;

    public AdminMenu (MenuManager menuManager) {
        this.menuManager = menuManager;
        menuManager.register("ADMIN", this);
    }
    @Override
    public void show() {
        WelcomeMessageHelper.printWelcomeMessage();
        System.out.println("A D M I N  M E N U");
        System.out.println("1. View the list of external servers and status");
        System.out.println("2. View the external server's details");
        System.out.println("3. Update/Edit the external server's details");
        System.out.println("4. Add new News Category");
        System.out.println("5. Logout");

        System.out.println("Enter Your Option: ");
        String choice = ScannerSingleton.getInstance().nextLine();
        switch (choice){
            case "1":
                // Logic to view the list of external servers and status
                adminService.viewExternalServersList();
                break;
            case "2":
                // Logic to view the external server's details
                adminService.viewExternalServersDetailsList();
                break;
            case "3":
                // Logic to update/edit the external server's details
                adminService.viewExternalServersDetailsList();
                System.out.println("Select the server you want to update:");
                String serverId = ScannerSingleton.getInstance().nextLine();
                System.out.println("Enter the new api key for the server:");
                String apiKey = ScannerSingleton.getInstance().nextLine();
                adminService.updateExternalServerDetails(serverId, apiKey);
                break;
            case "4":
                // Logic to add a new News Category
                adminService.viewAllCategories();
                String categoryName;
                while (true) {
                    System.out.println("Enter the new News Category name (one word only):");
                    categoryName = ScannerSingleton.getInstance().nextLine().trim();
                    if (!categoryName.isEmpty() && !categoryName.contains(" ")) {
                        break;
                    }
                    System.out.println("Invalid input. Please enter a single word (no spaces).");
                }
                String capitalizedName = categoryName.substring(0, 1).toUpperCase() + categoryName.substring(1).toLowerCase();
                adminService.addCategory(capitalizedName);
                break;
            case "5":
                // Logic to logout
                System.out.println("Logging out...");
                menuManager.navigateTo("MAIN");
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
        show();
    }
}
