package com.cnpm.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
import java.util.Optional;

import com.cnpm.DTO.UserDTO;
import com.cnpm.Entity.User;
import com.cnpm.Service.UserService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    List<User> findAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    ResponseEntity<User> findById(@PathVariable int id) {

        User user = userService.getUserByUserId(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return ResponseEntity.ok(user);
    }

    // POST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@RequestBody User user) {
        userService.saveUser(user);
    }

    // PUT 
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@RequestBody User user, @PathVariable int id) {
        userService.saveUser(user);
    }

    // DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        userService.deleteByUserId(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/change-password/{id}")
    ResponseEntity<?> changePassword(@PathVariable int id, @RequestBody String newPassword) {
        return ResponseEntity.ok(userService.changePassword(id, newPassword));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/change-info/{id}")
    ResponseEntity<?> changeInfo(@PathVariable int id, 
                                @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.changeInfo(id, userDTO.getFullName(), userDTO.getPhone(), userDTO.getAddress(), userDTO.getEmail()));
    }
}
