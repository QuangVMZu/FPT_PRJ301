package filter;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.AuthUtils;

@WebFilter(filterName = "AdminAuthorizationFilter", urlPatterns = {
    "/examHome.jsp",
    "/questionsForm.jsp",
    "/MainController",
    "/ExamsCategoryController"
})
public class AdminAuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AdminAuthorizationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String action = httpRequest.getParameter("action");
        String uri = httpRequest.getRequestURI();

        // Allow login/logout actions for all
        if ("login".equals(action) || "logout".equals(action)) {
            chain.doFilter(request, response);
            return;
        }

        // Handle STUDENT role
        if (AuthUtils.isStudent(httpRequest)) {
            if ("viewAllCategory".equals(action)
                    || "filterExamsByTitle".equals(action)
                    || "submitAnswer".equals(action)
                    || "viewQuestionsByExams".equals(action)
                    || "viewExamsByCategory".equals(action)) {
                chain.doFilter(request, response);
                return;
            } else {
                // Student trying to access restricted action/page
                httpRequest.setAttribute("checkError", "You do not have permission to access this page.");
                httpRequest.getRequestDispatcher("/welcome.jsp").forward(request, response);
                return;
            }
        }
        
        if (!AuthUtils.isStudent(httpRequest)) {
            if ("filterExamsByTitle".equals(action) || "submitAnswer".equals(action)) {
                httpRequest.setAttribute("checkError", "You do not have permission to access this page.");
                httpRequest.getRequestDispatcher("/welcome.jsp").forward(request, response);
                return;
            }
        }
        
        
        // Handle INSTRUCTOR role
        if (!AuthUtils.isInstructor(httpRequest)) {
            if (uri.endsWith("questionsForm.jsp") || uri.endsWith("examHome.jsp")) {
                httpRequest.setAttribute("checkError", "You do not have permission to access this page.");
                httpRequest.getRequestDispatcher("/welcome.jsp").forward(request, response);
                return;
            }
        }

        // If passed all checks, allow the request
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("AdminAuthorizationFilter destroyed");
    }
}
