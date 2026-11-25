package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ProductRequest;
import com.examly.springapp.dto.StockUpdateRequest;
import com.examly.springapp.model.Product;
import com.examly.springapp.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(request));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> getLowStockProducts() {
        return ResponseEntity.ok(productService.getLowStockProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/{id}/stock-in")
    public ResponseEntity<Product> stockIn(@PathVariable Long id, @Valid @RequestBody StockUpdateRequest request) {
        return ResponseEntity.ok(productService.stockIn(id, request.getQuantity()));
    }

    @PostMapping("/{id}/stock-out")
    public ResponseEntity<Product> stockOut(@PathVariable Long id, @Valid @RequestBody StockUpdateRequest request) {
        return ResponseEntity.ok(productService.stockOut(id, request.getQuantity()));
    }
}
