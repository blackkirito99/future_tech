package auth;

import org.apache.shiro.SecurityUtils;

import domain.User;

public class AppSession {
    public static final String USER_ATTRIBUTE_NAME = "user";
    public static final String CUSTOMER_ROLE = "CU";
    public static final String RETAILER_ROLE = "RT";

    public static boolean hasRole(String role) {
        return SecurityUtils.getSubject().hasRole(role);
    }

    public static boolean isAuthenticated() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    public static void init(User user) {
        SecurityUtils.getSubject().getSession().setAttribute(USER_ATTRIBUTE_NAME, user);
    }

    public static User getUser() {
        return (User) SecurityUtils.getSubject().getSession().getAttribute(USER_ATTRIBUTE_NAME);
    }
    
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }
}