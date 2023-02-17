package com.example.testmodule3.service;

import com.example.testmodule3.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    Product findProductById(Long id);
    void editProduct(Product product);
    void deleteProductById(Long id);
    void createProduct(Product product);

    List<Product> getAllProductsByCategoryId(long categoryId);
    List<Product> searchProductsByKw(String kw);
}
