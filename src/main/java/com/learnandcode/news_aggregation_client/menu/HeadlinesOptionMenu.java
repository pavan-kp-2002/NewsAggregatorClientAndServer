package com.learnandcode.news_aggregation_client.menu;

import com.learnandcode.news_aggregation_client.dto.ArticleDTO;
import com.learnandcode.news_aggregation_client.service.AdminService;
import com.learnandcode.news_aggregation_client.service.ArticlesService;
import com.learnandcode.news_aggregation_client.service.HeadlineService;
import com.learnandcode.news_aggregation_client.util.ArticlesDisplayOptionsHelper;
import com.learnandcode.news_aggregation_client.util.ScannerSingleton;
import com.learnandcode.news_aggregation_client.util.TokenStore;
import com.learnandcode.news_aggregation_client.util.WelcomeMessageHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HeadlinesOptionMenu implements Menu {
    private final MenuManager menuManager;
    private final HeadlineService headlineService;
    private final ArticlesDisplayOptionsHelper articlesDisplayOptionsHelper;

    public HeadlinesOptionMenu(MenuManager menuManager) {
        this.menuManager = menuManager;
        this.headlineService = new HeadlineService();
        this.articlesDisplayOptionsHelper = new ArticlesDisplayOptionsHelper(new ArticlesService());
    }

    private String getUserFriendlyDate(String prompt) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        inputFormat.setLenient(false);

        while (true) {
            System.out.print(prompt);
            String input = ScannerSingleton.getInstance().nextLine();
            try {
                Date date = inputFormat.parse(input);
                return outputFormat.format(date);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter the date in 'dd MMMM yyyy' format (e.g., 17 June 2025).");
            }
        }
    }

    @Override
    public void show(){
        WelcomeMessageHelper.printWelcomeMessage();
        System.out.println("H E A D L I N E S - O P T I O N S");
        System.out.println("1. Today");
        System.out.println("2. Date range");
        System.out.println("3. Back to User Menu");
        System.out.println("4. Logout");
        System.out.println("Enter Your Option: ");

        String choice = ScannerSingleton.getInstance().nextLine();
        switch (choice){
            case "1":
                List<ArticleDTO> todayArticles = headlineService.getTodaysArticles();
                if(todayArticles.isEmpty()){
                    System.out.println("No headlines available for today.");
                }else {
                    headlineService.printArticles(todayArticles, "T O D A Y' S - H E A D L I N E S");
                    articlesDisplayOptionsHelper.showPostArticleOptionsWithSave(menuManager, "HEADLINES");
                }
                break;
            case "2":
                String startDate = getUserFriendlyDate("Enter the start date (e.g., 17 June 2025): ");
                String endDate = getUserFriendlyDate("Enter the end date (e.g., 20 June 2025): ");
                AdminService adminService = new AdminService();
                System.out.println("Please choose the below options for Headlines:");
                adminService.viewAllCategories();

                System.out.print("Enter the category ID: ");
                String categoryIdInput = ScannerSingleton.getInstance().nextLine();
                int categoryId;
                try {
                    categoryId = Integer.parseInt(categoryIdInput);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid category ID.");
                    break;
                }
                headlineService.printArticles(headlineService.getArticlesByDateRangeAndCategory(startDate, endDate, categoryId), "H E A D L I N E S");
                articlesDisplayOptionsHelper.showPostArticleOptionsWithSave(menuManager, "HEADLINES");
                break;
            case "3":
                System.out.println("Returning to User Menu...");
                menuManager.navigateTo("USER");
                break;
            case "4":
                System.out.println("Logging out...");
                TokenStore.clear();
                menuManager.navigateTo("MAIN");
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
        show();
    }
}
