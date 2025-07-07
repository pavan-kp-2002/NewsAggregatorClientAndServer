package com.learnandcode.news_aggregation_client.menu;

import com.learnandcode.news_aggregation_client.service.ModerationService;
import com.learnandcode.news_aggregation_client.util.ScannerSingleton;

public class ModerationMenu implements Menu{
    private final ModerationService moderationService = new ModerationService();
    private final MenuManager menuManager;

    public ModerationMenu(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @Override
    public void show() {
        System.out.println("M O D E R A T I O N  M E N U");
        System.out.println("1. Hide an Article");
        System.out.println("2. Unhide an Article");
        System.out.println("3. Hide a Category");
        System.out.println("4. Unhide a Category");
        System.out.println("5. Block a Keyword");
        System.out.println("6. Unblock a Keyword");
        System.out.println("7. Back to Admin Menu");

        System.out.println("Enter Your Option: ");
        String choice = ScannerSingleton.getInstance().nextLine();
        switch (choice) {
            case "1":
                System.out.println("Enter Article ID to hide:");
                Long hideId = Long.parseLong(ScannerSingleton.getInstance().nextLine());
                moderationService.hideArticle(hideId);
                break;
            case "2":
                System.out.println("Enter Article ID to unhide:");
                Long unhideId = Long.parseLong(ScannerSingleton.getInstance().nextLine());
                moderationService.unhideArticle(unhideId);
                break;
            case "3":
                System.out.println("Enter Category ID to hide:");
                Long hideCatId = Long.parseLong(ScannerSingleton.getInstance().nextLine());
                moderationService.hideCategory(hideCatId);
                break;
            case "4":
                System.out.println("Enter Category ID to unhide:");
                Long unhideCatId = Long.parseLong(ScannerSingleton.getInstance().nextLine());
                moderationService.unhideCategory(unhideCatId);
                break;
            case "5":
                System.out.println("Enter keyword to block:");
                String blockKeyword = ScannerSingleton.getInstance().nextLine();
                moderationService.blockKeyword(blockKeyword);
                break;
            case "6":
                System.out.println("Enter keyword to unblock:");
                String unblockKeyword = ScannerSingleton.getInstance().nextLine();
                moderationService.unblockKeyword(unblockKeyword);
                break;
            case "7":
                menuManager.navigateTo("ADMIN");
                return;
            default:
                System.out.println("Invalid choice, please try again.");
        }
        show();
    }
}
