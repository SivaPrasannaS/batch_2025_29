package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.dto.ProductRequest;
import com.examly.springapp.exception.InsufficientStockException;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Product;
import com.examly.springapp.repository.ProductRepository;
import com.examly.springapp.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product addProduct(ProductRequest request) {
        Product product = new Product();
        product.setSku(request.getSku());
        product.setProductName(request.getProductName());
        product.setCategory(request.getCategory());
        product.setQuantityInStock(request.getQuantityInStock());
        product.setReorderLevel(request.getReorderLevel());
        product.setUnitPrice(request.getUnitPrice());
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    @Transactional
    public Product stockIn(Long id, Integer quantity) {
        Product product = getProductById(id);
        product.setQuantityInStock(product.getQuantityInStock() + quantity);
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product stockOut(Long id, Integer quantity) {
        Product product = getProductById(id);
        int available = product.getQuantityInStock();
        if (quantity > available) {
            throw new InsufficientStockException("Insufficient stock for product id: " + id
                    + ". Available: " + available + ", requested: " + quantity);
        }
        product.setQuantityInStock(available - quantity);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }
}
