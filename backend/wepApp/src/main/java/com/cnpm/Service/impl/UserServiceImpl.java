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
        return userRepository.findByName(name).orElse(null);
    }

    @Override
    public User getUserById(int id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return userRepository.findById(id).get();
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
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    
}
