package com.learnandcode.news_aggregation_client.menu;

import com.learnandcode.news_aggregation_client.service.CategoryConfigurationService;
import com.learnandcode.news_aggregation_client.service.KeywordConfigurationService;
import com.learnandcode.news_aggregation_client.util.ScannerSingleton;

public class CategoryConfigurationMenu implements Menu{
    private final MenuManager menuManager;
    private final CategoryConfigurationService categoryConfigurationService = new CategoryConfigurationService();
    public CategoryConfigurationMenu (MenuManager menuManager){
        this.menuManager = menuManager;
    }

    @Override
    public void show(){
        System.out.println("C A T E G O R Y - C O N F I G U R A T I O N");
        System.out.println("1. View Categories Configured");
        System.out.println("2. Edit Configured Categories");
        System.out.println("3. Back");
        System.out.print("Enter Your Option: ");
        String choice = ScannerSingleton.getInstance().nextLine();

        switch (choice){
            case "1":
                categoryConfigurationService.getAllCategoryConfigurations();
                break;
            case "2":
                String category;
                while (true) {
                    System.out.print("Enter category to edit: ");
                    category = ScannerSingleton.getInstance().nextLine();
                    if (category != null && !category.trim().isEmpty() && !category.trim().contains(" ")) {
                        break;
                    }
                    System.out.println("Category must be a single word. Please try again.");
                }
                categoryConfigurationService.editCategoryConfiguration(category);
                break;
            case "3":
                menuManager.navigateTo("CONFIGURE");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        show();
    }
}
