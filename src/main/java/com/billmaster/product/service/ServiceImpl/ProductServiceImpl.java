package com.billmaster.product.service.ServiceImpl;

import com.billmaster.product.dto.ProductRequest;
import com.billmaster.product.dto.ProductResponse;
import com.billmaster.product.model.Product;
import com.billmaster.product.repository.ProductRepository;
import com.billmaster.product.service.ProductService;

import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;

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
    @Override
    public void exportProducts(PrintWriter writer) {

        List<Product> products = productRepository.findAll();

        writer.println("Name,Price,Barcode,Quantity,Category");

        for (Product product : products) {
            writer.println(
                    product.getName() + "," +
                    product.getPrice() + "," +
                    product.getBarcode() + "," +
                    product.getQuantity() + "," +
                    product.getCategory()
            );
        }
    }
    @Override
    public void exportProductsToPDF(HttpServletResponse response) throws Exception {

        List<Product> products = productRepository.findAll();

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        document.add(new Paragraph("Product List"));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5);
        table.addCell("Name");
        table.addCell("Price");
        table.addCell("Barcode");
        table.addCell("Quantity");
        table.addCell("Category");

        for (Product product : products) {
            table.addCell(product.getName());
            table.addCell(String.valueOf(product.getPrice()));
            table.addCell(product.getBarcode());
            table.addCell(String.valueOf(product.getQuantity()));
            table.addCell(product.getCategory());
        }

        document.add(table);
        document.close();
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