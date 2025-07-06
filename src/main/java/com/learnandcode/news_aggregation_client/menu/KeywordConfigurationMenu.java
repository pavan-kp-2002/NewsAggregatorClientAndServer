package com.learnandcode.news_aggregation_client.menu;

import com.learnandcode.news_aggregation_client.dto.KeywordConfigurationDTO;
import com.learnandcode.news_aggregation_client.service.KeywordConfigurationService;
import com.learnandcode.news_aggregation_client.util.ScannerSingleton;

import java.util.List;

public class KeywordConfigurationMenu implements Menu{
    private final MenuManager menuManager;
    private final KeywordConfigurationService keywordConfigurationService= new KeywordConfigurationService();
    public KeywordConfigurationMenu(MenuManager menuManager1){
        this.menuManager = menuManager1;
    }

    @Override
    public void show() {
        System.out.println("K E Y W O R D   C O N F I G U R A T I O N");
        System.out.println("1. View Current Keywords");
        System.out.println("2. Add New Keyword");
        System.out.println("3. Edit Existing Keyword");
        System.out.println("4. Back");

        System.out.print("Enter Your Option: ");
        String choice = ScannerSingleton.getInstance().nextLine();

        switch (choice) {
            case "1":
                List<KeywordConfigurationDTO> configs = keywordConfigurationService.getUserKeywordConfigurations();
                if(configs.isEmpty()){
                    System.out.println("No keywords configured yet.");
                } else {
                    int slNo = 1;
                    for (KeywordConfigurationDTO config : configs) {
                        System.out.println(slNo + " " + config.getKeyword() + " - " + config.getStatus());
                        slNo++;
                    }
                }
                break;
            case "2":
                String newKeyword;
                while (true) {
                    System.out.print("Enter new keyword: ");
                    newKeyword = ScannerSingleton.getInstance().nextLine();
                    if (newKeyword != null && !newKeyword.trim().isEmpty() && !newKeyword.trim().contains(" ")) {
                        break;
                    }
                    System.out.println("Keyword must be a single word. Please try again.");
                }
                keywordConfigurationService.addKeywordConfiguration(newKeyword);
                break;
            case "3":
                String editKeyword;
                while (true) {
                    System.out.print("Enter keyword to edit: ");
                    editKeyword = ScannerSingleton.getInstance().nextLine();
                    if (editKeyword != null && !editKeyword.trim().isEmpty() && !editKeyword.trim().contains(" ")) {
                        break;
                    }
                    System.out.println("Keyword must be a single word. Please try again.");
                }
                keywordConfigurationService.updateKeywordConfiguration(editKeyword);
                break;
            case "4":
                menuManager.navigateTo("CONFIGURE");
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        show();
    }
}
