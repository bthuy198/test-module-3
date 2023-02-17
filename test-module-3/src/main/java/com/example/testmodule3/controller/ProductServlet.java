package com.example.testmodule3.controller;

import com.example.testmodule3.model.Category;
import com.example.testmodule3.model.Product;
import com.example.testmodule3.service.ICategoryService;
import com.example.testmodule3.service.IProductService;
import com.example.testmodule3.service.MSCategoryService;
import com.example.testmodule3.service.MSProductService;
import com.example.testmodule3.utils.ValidateUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/product")
public class ProductServlet extends HttpServlet {
    private ICategoryService iCategoryService;
    private IProductService iProductService;

    @Override
    public void init() throws ServletException {
        iCategoryService = new MSCategoryService();
        iProductService = new MSProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showCreateForm(request, response);
                break;
            case "delete":
                showDeleteForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            default:
                showProducts(request, response);
                break;
        }
    }

    private void showDeleteForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Product product = iProductService.findProductById(id);

        request.setAttribute("product", product);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/product/delete.jsp");
        requestDispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = iCategoryService.getAllCategories();
        request.setAttribute("categories", categories);
        long id = Long.parseLong(request.getParameter("id"));
        Product product = iProductService.findProductById(id);
        RequestDispatcher requestDispatcher;

        request.setAttribute("product", product);
        requestDispatcher = request.getRequestDispatcher("/product/edit.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = iCategoryService.getAllCategories();

        request.setAttribute("categories", categories);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/product/create.jsp");
        requestDispatcher.forward(request, response);
    }

    private void showProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = iProductService.getAllProducts();
        List<Category> categories = iCategoryService.getAllCategories();

        String kw = "";
        if (request.getParameter("kw") != null) {
            kw = request.getParameter("kw");
        }
        List<Product> productsSearch = iProductService.searchProductsByKw(kw);

        request.setAttribute("kw", kw);
        request.setAttribute("products", productsSearch);
        request.setAttribute("categories", categories);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/product/product.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                createProduct(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
                break;
            case "edit":
                editProduct(request, response);
                break;
            default:
                showProducts(request, response);
                break;
        }
    }

    private void editProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<String> errors = new ArrayList<>();
        List<Category> categories = iCategoryService.getAllCategories();
        request.setAttribute("categories", categories);
        Product product = new Product();

        isValidateName(request, product, errors);
        isValidatePrice(request, product, errors);
        isValidateQuantity(request, product, errors);
        isValidateCategory(request, product, errors);

        String description = request.getParameter("description");
        String color = request.getParameter("color");

        product.setDescription(description);
        product.setColor(color);

        if (errors.size() == 0) {
            long id = Long.parseLong(request.getParameter("id"));
            product.setId(id);
            iProductService.editProduct(product);
            response.sendRedirect("/product");
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/product/edit.jsp");
            request.setAttribute("errors", errors);
            request.setAttribute("product", product);
            requestDispatcher.forward(request, response);
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("idDelete"));
        iProductService.deleteProductById(id);

        response.sendRedirect("product?message=delete");
    }

    private void createProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = iCategoryService.getAllCategories();
        request.setAttribute("categories", categories);
        List<String> errors = new ArrayList<>();
        Product product = new Product();

        //long id, String name, double price, int quantity, String color, String description, long categoryId
        isValidateName(request, product, errors);
        isValidatePrice(request, product, errors);
        isValidateQuantity(request, product, errors);
        isValidateCategory(request, product, errors);

        long id = System.currentTimeMillis() / 10000;
        String color = request.getParameter("color");
        String description = request.getParameter("description");

        product.setId(id);
        product.setColor(color);
        product.setDescription(description);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/product/create.jsp");

        if (errors.size() == 0) {
            iProductService.createProduct(product);
            response.sendRedirect("/product");
        } else {
            request.setAttribute("errors", errors);
            request.setAttribute("product", product);
            requestDispatcher.forward(request, response);
        }
    }

    private void isValidateQuantity(HttpServletRequest request, Product product, List<String> errors) {
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        if (quantity < 0) {
            errors.add("Số lượng không hợp lệ");
        } else {
            product.setQuantity(quantity);
        }

    }

    private void isValidatePrice(HttpServletRequest request, Product product, List<String> errors) {
        double price = Double.parseDouble(request.getParameter("price"));
        if (price <= 0) {
            errors.add("Giá tiền không hợp lệ");
        } else {
            product.setPrice(price);
        }
    }

    private void isValidateName(HttpServletRequest request, Product product, List<String> errors) {
        String name = request.getParameter("name");
        List<Product> products = iProductService.getAllProducts();
        for(int i = 0; i < products.size(); i++){
            if(products.get(i).getName().equalsIgnoreCase(name)){
                errors.add("Sản phẩm đã tồn tại");
            }
        }
            product.setName(name);

    }

    private void isValidateCategory(HttpServletRequest request, Product product, List<String> errors) {
        try {
            long idCategory = Long.parseLong(request.getParameter("idCategory"));
            if (iCategoryService.getCategoryById(idCategory) != null) {
                product.setCategoryId(idCategory);
            } else {
                errors.add("Định dạng danh mục không hợp lệ");
            }
        } catch (NumberFormatException numberFormatException) {
            errors.add("Định dạng danh mục chưa đúng");
        }
    }
}
