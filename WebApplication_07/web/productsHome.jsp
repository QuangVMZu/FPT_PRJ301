<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO" %>
<%@page import="model.ProductDTO" %>
<%@page import="utils.AuthUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Form</title>
    </head>
    <body>
        <% if (AuthUtils.isAdmin(request)){
        
        String checkError = (String)request.getAttribute("checkError");
        String message = (String)request.getAttribute("message");
        ProductDTO product  = (ProductDTO)request.getAttribute("product");
        %>
        <h1>PRODUCT FORM</h1>
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="addProduct"/>

            <div> 
                <label for="id"> ID* </label> 
                <input type="text" id="id" name="id" required="required"
                       value="<%=product!=null?product.getId():""%>"
                       />
            </div>

            <div> 
                <label for="name"> Name* </label> 
                <input type="text" id="name" name="name" required="required"
                       value="<%=product!=null?product.getName():""%>"/>
            </div>

            <div> 
                <label for="image"> Image </label> 
                <input type="text" id="image" name="image"
                       value="<%=product!=null?product.getImage():""%>"/>
            </div>

            <div> 
                <label for="description"> Description </label> 
                <textarea  id="description" name="description">
                    <%=product!=null?product.getDescription():""%>
                </textarea>
            </div>

            <div> 
                <label for="price"> Price* </label> 
                <input type="number" id="price" name="price" required="required" min="0" step="0.01"
                       value="<%=product!=null?product.getPrice():""%>"/>
            </div>

            <div> 
                <label for="size"> Size </label> 
                <input type="text" id="size" name="size"
                       value="<%=product!=null?product.getSize():""%>"/>
            </div>

            <div> 
                <label for="status"> Status (Active Product) </label> 
                <input type="checkbox" id="status" name="status" value="true"
                       <%=product!=null&&product.isStatus()?" checked='checked' ":""%>
                       />
            </div>

            <div> 
                <input type="submit" value="Add Product"/>
                <input type="reset" value="Reset"/>    
            </div>
        </form>
        <% String idError = (String) request.getAttribute("idError"); %>
        <% String priceError = (String) request.getAttribute("priceError"); %>
        <% String addProductError = (String) request.getAttribute("addProductError"); %>

        <div style="color:red;">
            <% if (idError != null && !idError.isEmpty()) { %>
            <p><%= idError %></p>
            <% } %>
            <% if (priceError != null && !priceError.isEmpty()) { %>
            <p><%= priceError %></p>
            <% } %>
            <% if (addProductError != null && !addProductError.isEmpty()) { %>
            <p><%= addProductError %></p>
            <% } %>
        </div>

        <div style="color:green;">
            <% if (message != null && !message.isEmpty()) { %>
            <p><%= message %></p>
            <% } %>
        </div>
        <%
    }else {
        %>
        <%=AuthUtils.getAccessDeniedMessage("Product Form")%> 
        <%
    }
        %>


    </body>
</html>