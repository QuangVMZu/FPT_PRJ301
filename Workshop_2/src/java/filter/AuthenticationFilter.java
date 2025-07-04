package filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.AuthUtils;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {
    "/MainController",
    "/ExamsCategoryController",
    "/UserController",
    "/welcome.jsp",
    "/examsHome.jsp",
    "/examList.jsp",
    "/questionsForm.jsp",
    "/questionsHome.jsp",
    "/result.jsp"
})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AuthenticationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String action = httpRequest.getParameter("action"); // lấy action
        String uri = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        // ✅ Nếu là yêu cầu đăng nhập hoặc đăng xuất thì cho qua
        if ("login".equals(action) || "logout".equals(action)) {
            chain.doFilter(request, response);
            return;
        }

        if (!AuthUtils.isLoggedIn(httpRequest)) {
            httpRequest.setAttribute("checkError", "You do not have permission to access this page.");
            httpRequest.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("AuthenticationFilter destroyed");
    }
}
