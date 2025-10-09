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

import com.cnpm.Service.CartService;
import com.cnpm.Service.OrderService;
import com.cnpm.DTO.OrderDTO;
import com.cnpm.Entity.Order;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;

    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping("")
    List<Order> findAll() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    ResponseEntity<OrderDTO> findById(@PathVariable int id) {

        OrderDTO order = orderService.getOrderById(id);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        return ResponseEntity.ok(order);
    }

    // POST
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody Order order) {
        orderService.saveOrder(order);
    }

    // PUT 
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@RequestBody Order order, @PathVariable int id) {
        orderService.saveOrder(order);
    }

    // DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        orderService.deleteOrderById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("addFromCart/{userId}")
    void addOrderFromCart(@PathVariable int userId,
                        @RequestParam String payment,
                        @RequestParam String paymentMethod,
                        @RequestParam String shipping_address) {
        orderService.createOrderFromCart(userId, payment, paymentMethod, shipping_address);
    }
    
    // Cập nhật trạng thái đơn hàng
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/status")
    void updateOrderStatus(@PathVariable int id, 
                        @RequestParam String status) {
        orderService.updateOrderStatus(id, status);
    }
    
}
