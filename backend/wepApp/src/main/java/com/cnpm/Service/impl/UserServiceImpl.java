package com.cnpm.Service.impl;

import com.cnpm.Entity.User;
import com.cnpm.Repository.UserRepo;
import com.cnpm.Service.UserService;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;

    public UserServiceImpl(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByPhone(String phone) {
        return userRepository.findByPhone(phone).orElse(null);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByFullName(name).orElse(null);
    }

    @Override
    public User getUserByUserId(int id) {
        if (userRepository.findByUserId(id) == null) {
            throw new RuntimeException("User not found");
        }
        return userRepository.findByUserId(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteByUserId(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User changePassword(int id, String newPassword) {
        User user = getUserByUserId(id);
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    @Override
    public User changeInfo(int id, String fullName, String phone, String address, String email) {
        User user = userRepository.findByUserId(id);
        user.setFullName(fullName);
        user.setPhone(phone);
        user.setAddress(address);
        user.setEmail(email);
        return userRepository.save(user);
    }
    
}
