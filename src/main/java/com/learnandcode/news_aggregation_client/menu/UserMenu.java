package com.learnandcode.news_aggregation_client.menu;
import com.learnandcode.news_aggregation_client.dto.ArticleDTO;
import com.learnandcode.news_aggregation_client.dto.SavedArticleDTO;
import com.learnandcode.news_aggregation_client.service.ArticlesService;
import com.learnandcode.news_aggregation_client.service.HeadlineService;
import com.learnandcode.news_aggregation_client.util.ArticlesDisplayOptionsHelper;
import com.learnandcode.news_aggregation_client.util.ScannerSingleton;
import com.learnandcode.news_aggregation_client.util.TokenStore;

import java.util.List;

public class UserMenu implements Menu{
    private final MenuManager menuManager;
    private final ArticlesService articlesService;
    private final HeadlineService headlineService;
    private final ArticlesDisplayOptionsHelper articlesDisplayOptionsHelper;

    public UserMenu(MenuManager menuManager){
        this.menuManager = menuManager;
        this.articlesService = new ArticlesService();
        this.headlineService = new HeadlineService();
        this.articlesDisplayOptionsHelper = new ArticlesDisplayOptionsHelper(articlesService);
    }

    public void show() {
        System.out.println("U S E R - M E N U");
        System.out.println("1. Headlines");
        System.out.println("2. Saved Articles");
        System.out.println("3. Search");
        System.out.println("4. Notifications");
        System.out.println("5. Logout");
        System.out.println("Enter Your Option: ");
        String choice = ScannerSingleton.getInstance().nextLine();
        switch (choice) {
            case "1":
                menuManager.navigateTo("HEADLINES");
                break;
            case "2":
                // Show saved articles
                List<SavedArticleDTO> savedArticles = articlesService.showSavedArticles();
                if (savedArticles.isEmpty()) {
                    System.out.println("You have no saved articles.");
                } else {
                    articlesService.printArticles(savedArticles, "S A V E D - A R T I C L E S");
                    articlesDisplayOptionsHelper.showPostArticleOptionsWithDelete(menuManager, "USER");
                }
                break;
            case "3":
                // Show search outputs
                System.out.println("Enter the search term:");
                String searchTerm = ScannerSingleton.getInstance().nextLine();
                if (searchTerm.isEmpty()) {
                    System.out.println("Search term cannot be empty. Please try again.");
                    show();
                } else {
                    List<ArticleDTO> searchResults = headlineService.searchArticles(searchTerm);
                    if(searchResults.isEmpty()) {
                        System.out.println("No articles found for the search term: " + searchTerm);
                    } else {
                        headlineService.printArticles(searchResults, "S E A R C H - R E S U L T S");
                        articlesDisplayOptionsHelper.showPostArticleOptionsWithSave(menuManager, "USER");
                    }
                }
                break;
            case "4":
                // Show notifications
                menuManager.navigateTo("NOTIFICATIONS");
                break;
            case "5":
                System.out.println("Logging out...");
                TokenStore.clear();
                menuManager.navigateTo("MAIN");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        show();
    }
}
