package com.cnpm.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import com.cnpm.Entity.Product;
import com.cnpm.Service.ProductService;
import com.cnpm.DTO.ProductDTO;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    List<Product> findAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    ResponseEntity<Product> findById(@PathVariable int id) {

        Product product = productService.getProductById(id);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        return ResponseEntity.ok(product);
    }

    // POST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    // PUT 
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@RequestBody Product product, @PathVariable int id) {
        productService.saveProduct(product);
    }

    // DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        productService.deleteById(id);
    }

    // Cập nhật status sản phẩm
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/status")
    ResponseEntity<?> updateStatus(@PathVariable int id, @RequestParam String status) {
        return ResponseEntity.ok(productService.updateProductStatus(id, status));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/change-info")
    ResponseEntity<?> changeInfo(@PathVariable int id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.changeProductInfo(
                id,
                productDTO.getProductName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getImageUrl(),
                productDTO.isAvailable(),
                productDTO.getCategoryId()
        ));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{id}/add")
    ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.addProduct(
                productDTO.getProductName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getImageUrl(),
                productDTO.isAvailable(),
                productDTO.getCategoryId()
        ));
    }

}