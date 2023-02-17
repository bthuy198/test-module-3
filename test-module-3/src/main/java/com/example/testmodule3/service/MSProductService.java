package com.example.testmodule3.service;

import com.example.testmodule3.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MSProductService extends DBContext implements IProductService{
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM product";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM product where id = ?";
    private static final String DELETE_PRODUCT_BY_ID = "DELETE FROM `product` WHERE `id` = ?";
    private static final String INSERT_PRODUCT = "INSERT INTO `testmodule3`.`product` (`id`, `name`, `price`, `quantity`, `color`, `description`, `category_id`) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_PRODUCT_BY_CATEGORY_ID = "SELECT * FROM testmodule3.product where category_id = ?";
    private static final String UPDATE_PRODUCT_BY_ID = "UPDATE product SET name = ?, price = ?, quantity = ?, color = ?, description = ?, category_id = ?  WHERE id = ?";

    private static final String SEARCHING_CUSTOMER = "SELECT  * FROM product where `name` like ? ";

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_PRODUCTS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = getProductFromRs(rs);
                products.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    private Product getProductFromRs(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        int quantity = rs.getInt("quantity");
        String color = rs.getString("color");
        String description = rs.getString("description");

        long category_id = rs.getInt("category_id");

        Product p = new Product(id, name, price, quantity,color, description, category_id);
        return p;
    }

    @Override
    public Product findProductById(Long id) {
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_PRODUCT_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = getProductFromRs(rs);
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void editProduct(Product product) {
        Connection connection = getConnection();
        try {
           // "UPDATE product` SET `name` = ?,
            // `price` = ?, `quantity` = ?, `color` = ?, `description` = ? WHERE `id` = ?";

            PreparedStatement ps = connection.prepareStatement(UPDATE_PRODUCT_BY_ID);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getQuantity());
            ps.setString(4, product.getColor());
            ps.setString(5, product.getDescription());
            ps.setLong(6, product.getCategoryId());
            ps.setLong(7, product.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteProductById(Long id) {
        Connection connection = getConnection();
        //DELETE FROM customer` WHERE (`id` = ?)
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_PRODUCT_BY_ID);
            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
    }

    @Override
    public void createProduct(Product product) {
        Connection connection = getConnection();
        try {
            //INSERT INTO `testmodule3`.`product`
            // (`id`, `name`, `price`, `quantity`, `color`, `description`, `category_id`)
            PreparedStatement ps = connection.prepareStatement(INSERT_PRODUCT);
            ps.setLong(1, product.getId());
            ps.setString(2, product.getName());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getColor());
            ps.setString(6, product.getDescription());
            ps.setLong(7, product.getCategoryId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
    }

    @Override
    public List<Product> getAllProductsByCategoryId(long categoryId) {
        Connection connection = getConnection();
        List<Product> products = new ArrayList<>();
        try{
            PreparedStatement ps = connection.prepareStatement(SELECT_PRODUCT_BY_CATEGORY_ID);
            ps.setLong(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Product p = getProductFromRs(rs);
                products.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public List<Product> searchProductsByKw(String kw) {
        List<Product> products = new ArrayList<>();
        Connection connection = getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCHING_CUSTOMER);
            preparedStatement.setString(1, "%" + kw.trim() + "%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Product p = getProductFromRs(rs);
                products.add(p);
            }
            connection.close();
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
        return products;
    }
}
