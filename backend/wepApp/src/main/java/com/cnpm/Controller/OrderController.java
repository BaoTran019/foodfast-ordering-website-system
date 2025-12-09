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

import com.cnpm.Service.OrderService;
import com.cnpm.DTO.OrderDTO;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;

    }

    @GetMapping("")
    ResponseEntity<List<OrderDTO>> findAll() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
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
    void create(@RequestBody OrderDTO order) {
        orderService.saveOrder(order);
    }

    // PUT 
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@RequestBody OrderDTO order, @PathVariable int id) {
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
    ResponseEntity<OrderDTO> addOrderFromCart(@PathVariable int userId,
                        @RequestParam String payment_method,
                        @RequestParam String recipientName,
                        @RequestParam String recipientPhone,
                        @RequestParam String shipping_address) {
        return ResponseEntity.ok(orderService.createOrderFromCart(userId, payment_method, recipientName, recipientPhone, shipping_address));
    }
    
    // Cập nhật trạng thái đơn hàng
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/status")
    ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable int id, 
                        @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    // Lấy danh sách đơn hàng theo status
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/status/{status}")
    ResponseEntity<List<OrderDTO>> findByStatus(@PathVariable String status) {
        List<OrderDTO> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

}
