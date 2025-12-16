package com.cnpm.Service;

import com.cnpm.Entity.Product;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    Product getProductById(int id);

    List<Product> getAllProducts();

    Product saveProduct(Product product);
    
    void deleteById(int id);

    Product updateProductStatus(int id, String status);

    Product changeProductInfo(int id, String name, String description, double price, String imageUrl, boolean isAvailable, int categoryId);

    Product addProduct(String name, String description, double price, String imageUrl, boolean isAvailable, int categoryId);
}
