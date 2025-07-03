package com.learnandcode.news_aggregation_client.menu;

import com.learnandcode.news_aggregation_client.dto.AuthResult;
import com.learnandcode.news_aggregation_client.dto.LoginRequestDTO;
import com.learnandcode.news_aggregation_client.dto.SignupRequestDTO;
import com.learnandcode.news_aggregation_client.service.AuthService;
import com.learnandcode.news_aggregation_client.util.ScannerSingleton;
import com.learnandcode.news_aggregation_client.util.TokenStore;

public class MainMenu implements Menu {
    private final AuthService authService;
    private final MenuManager menuManager;

    public MainMenu(MenuManager menuManager){
        this.authService = new AuthService();
        this.menuManager = menuManager;

    }

    @Override
    public void show() {
        System.out.println("\nN E W S - A G G R E G A T O R");
        System.out.println("Welcome to the News Aggregation App!");
        System.out.println("Please choose an option:");
        System.out.println("1. Login");
        System.out.println("2. SignUp");
        System.out.println("3. Exit");

        String choice = ScannerSingleton.getInstance().nextLine();
        switch (choice){
            case "1":
                LoginRequestDTO loginRequestDTO = getLoginDetails();
                AuthResult loginResult = authService.login(loginRequestDTO);
                if(loginResult.success){
                    System.out.println(loginResult.message);
                    String userRole = TokenStore.getRole();
                    if("USER".equals(userRole)){
                        menuManager.navigateTo("USER");
                    } else if("ADMIN".equals(userRole)){
                        menuManager.navigateTo("ADMIN");
                    }
                }else {
                    System.out.println("Login failed: " + loginResult.message);
                    System.out.println("Try agian or SignUp if you don't have an account.");
                }
                break;
            case "2":
                SignupRequestDTO signupRequestDTO = getSignupDetails();
                AuthResult signupResult = authService.signUp(signupRequestDTO);
                if(signupResult.success){
                    System.out.println("Signup successful! You can now login.");
                } else {
                    System.out.println("Signup failed: " + signupResult.message);
                    System.out.println("Please try again.");
                }
                break;
            case "3":
                System.out.println("Exiting the application. Thank you!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice");
        }
        show();
    }

    private LoginRequestDTO getLoginDetails() {
        System.out.println("Enter your email:");
        String email = ScannerSingleton.getInstance().nextLine();
        System.out.println("Enter your password:");
        String password = ScannerSingleton.getInstance().nextLine();
        return new LoginRequestDTO(email, password);
    }

    private SignupRequestDTO getSignupDetails() {
        System.out.println("Enter your username:");
        String username = ScannerSingleton.getInstance().nextLine();
        System.out.println("Enter your email:");
        String email = ScannerSingleton.getInstance().nextLine();
        System.out.println("Enter your password:");
        String password = ScannerSingleton.getInstance().nextLine();
        return new SignupRequestDTO(username, email, password);
    }
}
