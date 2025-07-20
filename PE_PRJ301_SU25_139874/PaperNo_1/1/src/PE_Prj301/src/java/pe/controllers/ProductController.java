/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import pe.model.RoomForRentDao;
import pe.model.RoomForRentDto;
import pe.utils.AuthUtils;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ProductController", urlPatterns = {"/ProductController"})
public class ProductController extends HttpServlet {

    private final RoomForRentDao pdao = new RoomForRentDao();
    String url = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String action = request.getParameter("action");
            if ("search".equals(action)) {
                url = handleSearch(request, response);
            } else if ("delete".equals(action)) {
                url = handleDelete(request, response);
            } else {
                request.setAttribute("message", "Invalid action: " + action);
            }
        } catch (Exception e) {
            request.setAttribute("message", "Unexpected error: " + e.getMessage());
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

    private String handleSearch(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        if (AuthUtils.isLoggedIn(request)) {
            String keyword = request.getParameter("keyword");
            List<RoomForRentDto> list;

            if (keyword.trim() == null || keyword.trim().isEmpty()) {
                checkError = "";
            } else if (keyword != null && !keyword.trim().isEmpty()) {
                list = pdao.getProductsByName(keyword.trim());
                if (list == null || list.isEmpty()) {
                    checkError = "No data matching the search criteria found";
                }
                request.setAttribute("list", list);
            }
            request.setAttribute("keyword", keyword);
            request.setAttribute("checkError", checkError);
        }
        return "search.jsp";
    }

    private String handleDelete(HttpServletRequest request, HttpServletResponse response) {
        if (AuthUtils.isLoggedIn(request)) {
            String checkError = "";
            String message = "";

            try {
                int id = Integer.parseInt(request.getParameter("id"));

                RoomForRentDto product = new RoomForRentDto();
                product.setId(id);

                boolean success = pdao.update(product);
                if (success) {
                    message = "Product delete successfully.";
                } else {
                    checkError = "Cannot delete Product.";
                }

                request.setAttribute("checkError", checkError);
                request.setAttribute("message", message);
                request.setAttribute("product", product);
            } catch (Exception e) {
                request.setAttribute("checkError", "Error updating project: " + e.getMessage());
            }
        }
        return "search.jsp";
    }

}
