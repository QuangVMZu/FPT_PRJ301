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
import java.util.Arrays;
import java.util.List;

@WebFilter(filterName = "ProjectActionFilter", urlPatterns = {"/ExamsCategoryController"})
public class ProjectActionFilter implements Filter {
    
    // Danh sách các action cần quyền Admin
    private static final List<String> ADMIN_ACTIONS = Arrays.asList(
        "addQuestionsByExam", "addQuestionsByExam"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("ProductActionFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
                        FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String action = httpRequest.getParameter("action");
        
        // Kiểm tra nếu action cần quyền Admin
        if (action != null && ADMIN_ACTIONS.contains(action)) {
            if (!AuthUtils.isInstructor(httpRequest)) {
                // Không có quyền, chuyển về welcome với thông báo lỗi
                httpRequest.setAttribute("checkError", "You do not have permission to access this page.");
                httpRequest.getRequestDispatcher("/welcome.jsp").forward(request, response);
                return;
            }
        }
        
        // Cho phép tiếp tục
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("ProductActionFilter destroyed");
    }
}