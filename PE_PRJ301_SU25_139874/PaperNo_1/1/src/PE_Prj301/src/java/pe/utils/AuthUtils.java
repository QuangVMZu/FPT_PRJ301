package pe.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import pe.model.AccountDto;

public class AuthUtils {

    public static AccountDto getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            return (AccountDto) session.getAttribute("user");
        } else {
            return null;
        }
    }

    public static boolean isLoggedIn(HttpServletRequest request) {
        return getCurrentUser(request) != null;
    }

    public static boolean hasRole(HttpServletRequest request, int role) {
        AccountDto user = getCurrentUser(request);
        if (user != null) {
            return user.getRole() == role;
        } else {
            return false;
        }
    }

    public static boolean isAdmin(HttpServletRequest request) {
        return hasRole(request, 1);
    }

    public static boolean isMember(HttpServletRequest request) {
        return hasRole(request, 2);
    }

    public static String getLoginURL() {
        return "MainController";
    }

    public static String getAccessDeniedMessage(String action) {
        return "You can not access to " + action + ". Please contact administrator.";
    }
}
