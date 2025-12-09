package com.cnpm.Service.impl;

import com.cnpm.Service.ProductService;
import com.cnpm.Entity.Product;
import com.cnpm.Repository.ProductRepo;
import java.util.List;
import com.cnpm.Repository.CategoryRepo;

import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    public ProductServiceImpl(ProductRepo productRepo, CategoryRepo categoryRepo){
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Product getProductById(int id){
        if (productRepo.findById(id).isEmpty()){
            throw new RuntimeException("Product not found");
        }
        return productRepo.findById(id).get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product saveProduct(Product product){
        return productRepo.save(product);
    }

    @Override
    public void deleteById(int id) {
        productRepo.deleteById(id);
    }

    @Override
    public Product updateProductStatus(int id, String status) {
        Product product = productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        product.setAvailable(status.equals("true")? true : false);
        return productRepo.save(product);
    }

    @Override
    public Product changeProductInfo(int id, String productName, String description, double price, String imageUrl, boolean isAvailable, int categoryId) {
        Product product = productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        product.setProductName(productName);
        product.setDescription(description);
        product.setPrice(price);
        product.setAvailable(isAvailable);
        product.setImageUrl(imageUrl);
        product.setCategory(categoryRepo.findByCategoryId(categoryId).orElseThrow(() -> new RuntimeException("Category not found")));

        return productRepo.save(product);
    }

    @Override
    public Product addProduct(String productName, String description, double price, String imageUrl, boolean isAvailable, int categoryId) {
        Product product = new Product();

        product.setProductName(productName);
        product.setDescription(description);
        product.setPrice(price);
        product.setAvailable(isAvailable);
        product.setImageUrl(imageUrl);
        product.setCategory(categoryRepo.findByCategoryId(categoryId).orElseThrow(() -> new RuntimeException("Category not found")));

        return productRepo.save(product);
    }
}
