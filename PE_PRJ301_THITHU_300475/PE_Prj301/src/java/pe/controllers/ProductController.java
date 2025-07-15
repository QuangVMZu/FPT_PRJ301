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
import pe.model.FashionDao;
import pe.model.FashionDto;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ProductController", urlPatterns = {"/ProductController"})
public class ProductController extends HttpServlet {

    private final FashionDao pdao = new FashionDao();
    String url = "login.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String action = request.getParameter("action");
            if ("search".equals(action)) {
                url = handleSearch(request, response);
            } else if ("edit".equals(action)) {
                url = handleEdit(request, response);
            } else if ("update".equals(action)) {
                url = handleUpdate(request, response);
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
        String keyword = request.getParameter("keyword");
        List<FashionDto> list;

        if (keyword != null && !keyword.trim().isEmpty()) {
            list = pdao.getProductsByName(keyword.trim());
            if (list == null || list.isEmpty()) {
                checkError = "No data matching the search criteria found";
            }
            request.setAttribute("list", list);
        }

        request.setAttribute("keyword", keyword);
        request.setAttribute("checkError", checkError);

        return "search.jsp";
    }

    private String handleEdit(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String keyword = request.getParameter("keyword");
        FashionDto product = pdao.getProjectByID(id);
        System.out.println(product);
        if (product != null) {
            request.setAttribute("keyword", keyword);
            request.setAttribute("product", product);

        }
        return "update.jsp";
    }

    private String handleUpdate(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";

        try {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            float price = Float.parseFloat(request.getParameter("price"));
            String size = request.getParameter("size");
            int status = Integer.parseInt(request.getParameter("status"));

            FashionDto product = new FashionDto(id, name, description, price, size, status);

            if (price < 0) {
                checkError = "Price must be more than zero!";
            } else if (status != 0 && status != 1) {
                checkError = "Status must be 0 or 1!";
            } else if (size == null
                    || (!size.equalsIgnoreCase("S")
                    && !size.equalsIgnoreCase("M")
                    && !size.equalsIgnoreCase("L")
                    && !size.equalsIgnoreCase("X")
                    && !size.equalsIgnoreCase("XL")
                    && !size.equalsIgnoreCase("XXL")
                    && !size.equalsIgnoreCase("XXXL")
                    && !size.equalsIgnoreCase("XXXXL"))) {
                checkError = "Size not valid!";
            } else {
                boolean success = pdao.update(product);
                if (success) {
                    message = "Product updated successfully.";
                } else {
                    checkError = "Cannot update Product.";
                }
            }

            request.setAttribute("checkError", checkError);
            request.setAttribute("message", message);
            request.setAttribute("product", product);
        } catch (Exception e) {
            request.setAttribute("checkError", "Error updating project: " + e.getMessage());
        }
        return "update.jsp";
    }

}
