package com.example.testmodule3.service;

import com.example.testmodule3.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(long id);
}
