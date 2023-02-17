package com.example.testmodule3.service;

import com.example.testmodule3.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MSCategoryService extends DBContext implements ICategoryService{
    private static final String GET_ALL_CATEGORIES = "SELECT * FROM category";
    private static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM category where id = ?";

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_ALL_CATEGORIES);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category c = getCategoryFromRs(rs);
                categories.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    private Category getCategoryFromRs(ResultSet rs) throws SQLException {
        Category category = new Category();
        long id = rs.getLong("id");
        String name = rs.getString("name");

        category.setId(id);
        category.setName(name);
        return category;
    }

    @Override
    public Category getCategoryById(long id) {
        Connection connection = getConnection();
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORY_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Category c = getCategoryFromRs(rs);
                return c;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }
}
