package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.StartupProjectsDAO;
import model.StartupProjectsDTO;

@WebServlet(name = "ProjectController", urlPatterns = {"/ProjectController"})
public class ProjectController extends HttpServlet {

    private final StartupProjectsDAO pdao = new StartupProjectsDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "welcome.jsp";
        try {
            String action = request.getParameter("action");

            if (action == null || action.isEmpty()) {
                url = handleViewAllProjects(request, response); // default to view all
            } else if (action.equals("addProject")) {
                url = handleProjectCreate(request, response);
            } else if (action.equals("searchProject")) {
                url = handleProjectSearching(request, response);
            } else if (action.equals("editProject")) {
                url = handleProductEditing(request, response);
            } else if (action.equals("updateProject")) {
                url = handleProjectUpdating(request, response);
            } else if (action.equals("viewAllProjects")) {
                url = handleViewAllProjects(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("checkError", "Unexpected error: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String handleProjectCreate(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";

        try {
            int projectID = Integer.parseInt(request.getParameter("project_id"));
            String projectName = request.getParameter("project_name");
            String description = request.getParameter("description");
            String status = request.getParameter("status");
            String estimated = request.getParameter("estimated_launch");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date estimatedDate;

            try {
                estimatedDate = formatter.parse(estimated);
            } catch (ParseException e) {
                request.setAttribute("checkError", "Invalid date format for estimated date.");
                request.setAttribute("project", new StartupProjectsDTO(projectID, projectName, description, status, null));
                return "projectHome.jsp";
            }

            StartupProjectsDTO project = new StartupProjectsDTO(projectID, projectName, description, status, estimatedDate);

            if (pdao.isProjectExists(projectID)) {
                checkError = "Project ID already exists.";
            } else if (!estimatedDate.after(new Date())) {
                checkError = "Estimated launch date must be in the future.";
            } else {
                if (pdao.create(project)) {
                    message = "Add project successfully.";
                } else {
                    checkError = "Cannot add new project.";
                }
            }

            request.setAttribute("checkError", checkError);
            request.setAttribute("message", message);
            request.setAttribute("project", project);

        } catch (Exception e) {
            request.setAttribute("checkError", "Error creating project: " + e.getMessage());
        }

        return "projectHome.jsp";
    }

    private String handleProjectUpdating(HttpServletRequest request, HttpServletResponse response) {

        String checkError = "";
        String message = "";
        try {
            int projectID = Integer.parseInt(request.getParameter("project_id"));
            String projectName = request.getParameter("project_name");
            String description = request.getParameter("description");
            String status = request.getParameter("status");
            String estimated = request.getParameter("estimated_launch");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date estimatedDate;

            try {
                estimatedDate = formatter.parse(estimated);
            } catch (ParseException e) {
                request.setAttribute("checkError", "Invalid date format for estimated date.");
                request.setAttribute("project", new StartupProjectsDTO(projectID, projectName, description, status, null));
                request.setAttribute("isEdit", true);
                return "projectHome.jsp";
            }

            StartupProjectsDTO project = new StartupProjectsDTO(projectID, projectName, description, status, estimatedDate);

            if (!estimatedDate.after(new Date())) {
                checkError = "Estimated launch date must be in the future.";
            } else if (pdao.update(project)) {
                message = "Project updated successfully.";
            } else {
                checkError = "Cannot update project.";
            }

            request.setAttribute("checkError", checkError);
            request.setAttribute("message", message);
            request.setAttribute("project", project);
            request.setAttribute("isEdit", true);
        } catch (Exception e) {
            request.setAttribute("checkError", "Error updating project: " + e.getMessage());
        }
        return "projectHome.jsp";
    }

    private String handleProductEditing(HttpServletRequest request, HttpServletResponse response) {
        int projectID = Integer.parseInt(request.getParameter("project_id"));
        String keyword = request.getParameter("keyword");
        StartupProjectsDTO project = pdao.getProjectByID(projectID);
        if (project != null) {
            request.setAttribute("keyword", keyword);
            request.setAttribute("project", project);
            request.setAttribute("isEdit", true);

        }
        return "projectHome.jsp";
    }

    private String handleProjectSearching(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String keyword = request.getParameter("keyword");
        List<StartupProjectsDTO> list;

        if (keyword != null && !keyword.trim().isEmpty()) {
            list = pdao.getProductsByName(keyword.trim());
            if (list == null || list.isEmpty()) {
                checkError = "No projects found with name: " + keyword;
            }
            request.setAttribute("list", list);
        }
        else {
            list = pdao.getAll();
        }

        request.setAttribute("keyword", keyword);
        request.setAttribute("list", list);
        request.setAttribute("checkError", checkError);

        return "welcome.jsp";
    }

    private String handleViewAllProjects(HttpServletRequest request, HttpServletResponse response) {
        List<StartupProjectsDTO> list = pdao.getAll();
        request.setAttribute("list", list);
        return "welcome.jsp";
    }
}
