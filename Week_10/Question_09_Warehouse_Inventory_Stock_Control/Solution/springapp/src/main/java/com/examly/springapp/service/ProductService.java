package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.ProductRequest;
import com.examly.springapp.model.Product;

public interface ProductService {

    Product addProduct(ProductRequest request);

    Product getProductById(Long id);

    Product stockIn(Long id, Integer quantity);

    Product stockOut(Long id, Integer quantity);

    List<Product> getLowStockProducts();
}
