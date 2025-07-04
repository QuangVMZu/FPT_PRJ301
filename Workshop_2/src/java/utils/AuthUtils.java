package utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.UserDTO;

public class AuthUtils {

    public static UserDTO getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            return (UserDTO) session.getAttribute("user");
        } else {
            return null;
        }
    }

    public static boolean isLoggedIn(HttpServletRequest request) {
        return getCurrentUser(request) != null;
    }

    public static boolean hasRole(HttpServletRequest request, String role) {
        UserDTO user = getCurrentUser(request);
        if (user != null) {
            return user.getRole().equals(role);
        } else {
            return false;
        }
    }

    public static boolean isInstructor(HttpServletRequest request) {
        return hasRole(request, "Instructor");
    }

    public static boolean isStudent(HttpServletRequest request) {
        return hasRole(request, "Student");
    }

    public static String getLoginURL() {
        return "MainController";
    }

    public static String getAccessDeniedMessage(String action) {
        return "You can not access to " + action + ". Please contact administrator.";
    }
}
