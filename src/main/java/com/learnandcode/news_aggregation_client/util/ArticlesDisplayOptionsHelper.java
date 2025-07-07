package com.learnandcode.news_aggregation_client.util;

import com.learnandcode.news_aggregation_client.menu.MenuManager;
import com.learnandcode.news_aggregation_client.service.ArticlesService;
import com.learnandcode.news_aggregation_client.service.HeadlineService;
import com.learnandcode.news_aggregation_client.service.ModerationService;

public class ArticlesDisplayOptionsHelper {
    private final ArticlesService articlesService;
    private final HeadlineService headlineService = new HeadlineService();
    private final ModerationService moderationService = new ModerationService();

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
            case "4":
                System.out.print("Enter Article Id to like: ");
                String likeIdInput = ScannerSingleton.getInstance().nextLine();
                try {
                    long articleId = Long.parseLong(likeIdInput);
                    boolean result = headlineService.reactToArticle(articleId, "LIKE");
                    System.out.println(result ? "Article liked." : "Failed to like article.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Article Id. Please enter a valid number.");
                }
                break;
            case "5":
                System.out.print("Enter Article Id to dislike: ");
                String dislikeIdInput = ScannerSingleton.getInstance().nextLine();
                try {
                    long articleId = Long.parseLong(dislikeIdInput);
                    boolean result = headlineService.reactToArticle(articleId, "DISLIKE");
                    System.out.println(result ? "Article disliked." : "Failed to dislike article.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Article Id. Please enter a valid number.");
                }
                break;
            case "6":
                System.out.println("Enter Article Id to Report:");
                String articleIdToReport = ScannerSingleton.getInstance().nextLine();
                try {
                    long articleId = Long.parseLong(articleIdToReport);
                    moderationService.reportArticle(articleId);
                }catch (NumberFormatException e) {
                    System.out.println("Invalid Article Id. Please enter a valid number.");
                    return;
                }
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
