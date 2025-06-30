package com.learnandcode.news_aggregation_client.menu;

import com.learnandcode.news_aggregation_client.dto.AuthResult;
import com.learnandcode.news_aggregation_client.dto.LoginRequestDTO;
import com.learnandcode.news_aggregation_client.dto.SignupRequestDTO;
import com.learnandcode.news_aggregation_client.service.AuthService;
import com.learnandcode.news_aggregation_client.util.ScannerSingleton;
import com.learnandcode.news_aggregation_client.util.TokenStore;

public class MainMenu {
    private final AuthService authService = new AuthService();
    private final UserMenu userMenu = new UserMenu();
    private final AdminMenu adminMenu = new AdminMenu();
    public void show() {
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
                    System.out.println("Login successful!");
                    String userRole = TokenStore.getRole();
                    if("USER".equals(userRole)){
                        userMenu.show();
                    }else if("ADMIN".equals(userRole)) {
                        adminMenu.show();
                    }else {
                        System.out.println("Unknown role: " + userRole);
                    }
                }else {
                    System.out.println("Login failed: " + loginResult.message);
                }
                break;
            case "2":
                SignupRequestDTO signupRequestDTO = getSignupDetails();
                AuthResult signupResult = authService.signUp(signupRequestDTO);
                if(signupResult.success){
                    System.out.println(signupResult.message);
                    System.out.println("Please login to continue.");
                    show();
                }else {
                    System.out.println("Sign-up failed: " + signupResult.message);
                }
                break;
            case "3":
                System.out.println("Exiting the application. Thank you!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice");
        }
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
