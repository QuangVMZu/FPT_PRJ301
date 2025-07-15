package pe.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import pe.model.UserDto;

public class AuthUtils {

    public static UserDto getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            return (UserDto) session.getAttribute("user");
        } else {
            return null;
        }
    }

    public static boolean isLoggedIn(HttpServletRequest request) {
        return getCurrentUser(request) != null;
    }

    public static boolean hasRole(HttpServletRequest request, String role) {
        UserDto user = getCurrentUser(request);
        if (user != null) {
            return user.getRoleID().equals(role);
        } else {
            return false;
        }
    }

    public static boolean isAdmin(HttpServletRequest request) {
        return hasRole(request, "AD");
    }

    public static boolean isMember(HttpServletRequest request) {
        return hasRole(request, "MB");
    }

    public static String getLoginURL() {
        return "MainController";
    }

    public static String getAccessDeniedMessage(String action) {
        return "You can not access to " + action + ". Please contact administrator.";
    }
}
