package com.billmaster.product.service.ServiceImpl;
import com.billmaster.product.dto.ProductRequest;
import com.billmaster.product.dto.ProductResponse;
import com.billmaster.product.model.Product;
import com.billmaster.product.repository.ProductRepository;
import com.billmaster.product.service.ProductService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        Product product = new Product(
                null,
                request.getName(),
                request.getPrice(),
                request.getStock(),
                request.getCategory()
        );

        Product saved = productRepository.save(product);

        return mapToResponse(saved);
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(String id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return mapToResponse(product);
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    private ProductResponse mapToResponse(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getCategory()
        );
    }
}
