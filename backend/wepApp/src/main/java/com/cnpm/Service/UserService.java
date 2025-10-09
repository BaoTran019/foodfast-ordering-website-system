package com.cnpm.Service;

import com.cnpm.Entity.User;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getUserByName(String name);

    User getUserByPhone(String phone);
    
    User getUserById(int id);

    List<User> getAllUsers();

    User saveUser(User user);
    
    void deleteById(int id);

}