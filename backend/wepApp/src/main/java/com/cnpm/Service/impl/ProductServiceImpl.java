package com.cnpm.Service.impl;

import com.cnpm.Service.ProductService;
import com.cnpm.Entity.Product;
import com.cnpm.Repository.ProductRepo;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo){
        this.productRepo = productRepo;
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
}
