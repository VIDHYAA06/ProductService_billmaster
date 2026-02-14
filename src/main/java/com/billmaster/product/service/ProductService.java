package com.billmaster.product.service;

import com.billmaster.product.dto.ProductRequest;
import com.billmaster.product.dto.ProductResponse;

import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(String id);
void exportProductsToPDF(HttpServletResponse response) throws Exception;
    void deleteProduct(String id);
    void exportProducts(PrintWriter writer);
}
