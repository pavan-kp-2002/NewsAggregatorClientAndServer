package com.learnandcode.news_aggregation_client.util;

import com.learnandcode.news_aggregation_client.menu.MenuManager;
import com.learnandcode.news_aggregation_client.service.ArticlesService;

public class ArticlesDisplayOptionsHelper {
    private final ArticlesService articlesService;

    public ArticlesDisplayOptionsHelper(ArticlesService articlesService) {
            this.articlesService = articlesService;
        }

    public void showPostArticleOptionsWithSave(MenuManager menuManager, String menuName) {
        String choice = ScannerSingleton.getInstance().nextLine();
        switch (choice) {
            case "1":
                System.out.println("Returning to " + menuName + " menu...");
                menuManager.navigateTo(menuName);
                break;
            case "2":
                System.out.println("Logging out...");
                TokenStore.clear();
                menuManager.navigateTo("MAIN");
                break;
            case "3":
                System.out.print("Enter Article Id to save: ");
                String articleIdInput = ScannerSingleton.getInstance().nextLine();
                try {
                    long articleId = Long.parseLong(articleIdInput);
                    System.out.println(articlesService.saveArticle(articleId));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Article Id. Please enter a valid number.");
                }
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }

    public void showPostArticleOptionsWithDelete(MenuManager menuManager, String menuName) {
        String choice = ScannerSingleton.getInstance().nextLine();
        switch (choice) {
            case "1":
                System.out.println("Returning to " + menuName + " menu...");
                menuManager.navigateTo(menuName);
                break;
            case "2":
                System.out.println("Logging out...");
                TokenStore.clear();
                menuManager.navigateTo("MAIN");
                break;
            case "3":
                System.out.print("Enter Article Id to delete: ");
                String articleIdInput = ScannerSingleton.getInstance().nextLine();
                try {
                    long articleId = Long.parseLong(articleIdInput);
                    System.out.println(articlesService.deleteArticle(articleId));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Article Id. Please enter a valid number.");
                }
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }
}
