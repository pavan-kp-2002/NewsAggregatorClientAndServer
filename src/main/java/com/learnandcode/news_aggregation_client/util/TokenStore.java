package com.learnandcode.news_aggregation_client.util;

public class TokenStore {
    private static String token;
    private static String role;
    private static String userName;

    private TokenStore() {}

    public static void setToken(String jwtToken) {
        token = jwtToken;
    }

    public static String getToken() {
        return token;
    }

    public static void clear() {
        token = null;
        role = null;
    }

    public static void setRole(String userRole) {
        role = userRole;
    }

    public static String getRole() {
        return role;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        TokenStore.userName = userName;
    }
}
