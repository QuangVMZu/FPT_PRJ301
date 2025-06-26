package filter;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.AuthUtils;

@WebFilter(filterName = "AdminAuthorizationFilter", urlPatterns = {
    "/projectHome.jsp",
    "/MainController",
    "/ProjectController"
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
        
        if ("login".equals(action) || "logout".equals(action) || "searchProduct".equals(action)) {
            chain.doFilter(request, response);
            return;
        }

        if (!AuthUtils.isAdmin(httpRequest)) {
            if ("updateProduct".equals(action) || "editProduct".equals(action)
                    || "changeProductStatus".equals(action) || "searchProduct".equals(action) 
                    || "addProduct".equals(action)) {
                httpRequest.setAttribute("checkError", "You do not have permission to access this page.");
                httpRequest.getRequestDispatcher("/welcome.jsp").forward(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("AdminAuthorizationFilter destroyed");
    }
}
