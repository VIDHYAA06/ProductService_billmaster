package com.billmaster.product.service;

import com.billmaster.product.dto.ProductRequest;
import com.billmaster.product.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(String id);

    void deleteProduct(String id);
}
