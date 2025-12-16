package com.cnpm.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cnpm.Entity.Category;
import com.cnpm.Entity.Product;
import com.cnpm.Repository.CategoryRepo;
import com.cnpm.Repository.ProductRepo;
import com.cnpm.Service.impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService; // Class cần test

    @Mock
    private ProductRepo productRepo;

    @Mock
    private CategoryRepo categoryRepo;

    // Dữ liệu mẫu dùng chung
    private Product mockProduct;
    private Category mockCategory;

    @BeforeEach
    void setUp() {
        mockCategory = new Category();
        mockCategory.setCategoryId(1);
        mockCategory.setCategoryName("Electronics");

        mockProduct = new Product();
        mockProduct.setProductId(100);
        mockProduct.setProductName("Old Name");
        mockProduct.setPrice(100.0);
        mockProduct.setAvailable(false);
        mockProduct.setCategory(mockCategory);
    }

    // =========================================================
    // TEST CASE: Xem chi tiết sản phẩm (Get By ID)
    // =========================================================

    @Test
    void getProductById_WhenExists_ShouldReturnProduct() {
        // 1. ARRANGE
        int productId = 100;
        when(productRepo.findById(productId)).thenReturn(Optional.of(mockProduct));

        // 2. ACT
        Product result = productService.getProductById(productId);

        // 3. ASSERT
        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        assertEquals("Old Name", result.getProductName());
    }

    @Test
    void getProductById_WhenNotFound_ShouldThrowException() {
        // 1. ARRANGE
        int productId = 999;
        when(productRepo.findById(productId)).thenReturn(Optional.empty());

        // 2. ACT & ASSERT
        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.getProductById(productId);
        });

        assertEquals("Product not found", exception.getMessage());
    }

    // =========================================================
    // 1. TEST: updateProductStatus
    // =========================================================

    // @Test
    // void updateProductStatus_WhenProductExists_ShouldUpdateStatus() {
    //     // 1. ARRANGE
    //     int productId = 100;
    //     String newStatus = "true";

    //     when(productRepo.findById(productId)).thenReturn(Optional.of(mockProduct));
    //     when(productRepo.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

    //     // 2. ACT
    //     Product result = productService.updateProductStatus(productId, newStatus);

    //     // 3. ASSERT
    //     assertTrue(result.isAvailable()); // String "true" -> boolean true
    //     verify(productRepo).save(mockProduct);
    // }

    // @Test
    // void updateProductStatus_WhenStatusStringIsNotTrue_ShouldSetFalse() {
    //     // 1. ARRANGE
    //     // Logic code của bạn: status.equals("true") ? true : false
    //     // Nên input "abcd" hay "false" đều sẽ ra false
    //     when(productRepo.findById(100)).thenReturn(Optional.of(mockProduct));
    //     when(productRepo.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

    //     // 2. ACT
    //     Product result = productService.updateProductStatus(100, "anything_else");

    //     // 3. ASSERT
    //     assertFalse(result.isAvailable());
    // }

    // @Test
    // void updateProductStatus_WhenProductNotFound_ShouldThrowException() {
    //     // 1. ARRANGE
    //     when(productRepo.findById(999)).thenReturn(Optional.empty());

    //     // 2. ACT & ASSERT
    //     RuntimeException exception = assertThrows(RuntimeException.class, () -> {
    //         productService.updateProductStatus(999, "true");
    //     });
    //     assertEquals("Product not found", exception.getMessage());
    // }

    // // =========================================================
    // // 2. TEST: addProduct
    // // =========================================================

    // @Test
    // void addProduct_WhenCategoryExists_ShouldSaveNewProduct() {
    //     // 1. ARRANGE
    //     int categoryId = 1;
    //     when(categoryRepo.findByCategoryId(categoryId)).thenReturn(Optional.of(mockCategory));
    //     when(productRepo.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

    //     // 2. ACT
    //     Product result = productService.addProduct(
    //         "New Phone", "Description", 500.0, "img.jpg", true, categoryId
    //     );

    //     // 3. ASSERT
    //     assertNotNull(result);
    //     assertEquals("New Phone", result.getProductName());
    //     assertEquals(500.0, result.getPrice());
    //     assertEquals(mockCategory, result.getCategory());
        
    //     verify(categoryRepo).findByCategoryId(categoryId);
    //     verify(productRepo).save(any(Product.class));
    // }

    // @Test
    // void addProduct_WhenCategoryNotFound_ShouldThrowException() {
    //     // 1. ARRANGE
    //     when(categoryRepo.findByCategoryId(99)).thenReturn(Optional.empty());

    //     // 2. ACT & ASSERT
    //     RuntimeException exception = assertThrows(RuntimeException.class, () -> {
    //         productService.addProduct("Name", "Desc", 10.0, "url", true, 99);
    //     });
    //     assertEquals("Category not found", exception.getMessage());
        
    //     // Đảm bảo không save product nếu category sai
    //     verify(productRepo, never()).save(any(Product.class));
    // }

    // // =========================================================
    // // 3. TEST: changeProductInfo
    // // =========================================================

    // @Test
    // void changeProductInfo_WhenEverythingValid_ShouldUpdateFields() {
    //     // 1. ARRANGE
    //     int productId = 100;
    //     int newCategoryId = 2;
    //     Category newCategory = new Category();
    //     newCategory.setCategoryId(2);
    //     newCategory.setCategoryName("Books");

    //     // Tìm thấy Product cũ
    //     when(productRepo.findById(productId)).thenReturn(Optional.of(mockProduct));
    //     // Tìm thấy Category mới để cập nhật
    //     when(categoryRepo.findByCategoryId(newCategoryId)).thenReturn(Optional.of(newCategory));
    //     // Save
    //     when(productRepo.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

    //     // 2. ACT
    //     Product result = productService.changeProductInfo(
    //         productId, "Updated Name", "New Desc", 200.0, "new_img.png", true, newCategoryId, 10.0
    //     );

    //     // 3. ASSERT
    //     assertEquals("Updated Name", result.getProductName()); // Tên đã đổi
    //     assertEquals(200.0, result.getPrice()); // Giá đã đổi
    //     assertEquals("Books", result.getCategory().getCategoryName()); // Category đã đổi
    //     assertEquals(10.0, result.getDiscount());
        
    //     verify(productRepo).save(mockProduct);
    // }

    // @Test
    // void changeProductInfo_WhenProductNotFound_ShouldThrowException() {
    //     // 1. ARRANGE
    //     when(productRepo.findById(999)).thenReturn(Optional.empty());

    //     // 2. ACT & ASSERT
    //     Exception e = assertThrows(RuntimeException.class, () -> {
    //         productService.changeProductInfo(999, "N", "D", 1, "I", true, 1, 0);
    //     });
    //     assertEquals("Product not found", e.getMessage());
    // }

    // @Test
    // void changeProductInfo_WhenCategoryNotFound_ShouldThrowException() {
    //     // 1. ARRANGE
    //     // Tìm thấy Product nhưng KHÔNG tìm thấy Category mới
    //     when(productRepo.findById(100)).thenReturn(Optional.of(mockProduct));
    //     when(categoryRepo.findByCategoryId(99)).thenReturn(Optional.empty());

    //     // 2. ACT & ASSERT
    //     Exception e = assertThrows(RuntimeException.class, () -> {
    //         productService.changeProductInfo(100, "N", "D", 1, "I", true, 99, 0);
    //     });
    //     assertEquals("Category not found", e.getMessage());
    // }
}
