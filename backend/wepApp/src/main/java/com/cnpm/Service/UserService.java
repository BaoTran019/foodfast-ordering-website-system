package com.cnpm.Service;

import com.cnpm.Entity.User;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getUserByName(String name);

    User getUserByPhone(String phone);
    
    User getUserByUserId(int id);

    List<User> getAllUsers();

    User saveUser(User user);
    
    void deleteByUserId(int id);

    User changePassword(int id, String newPassword);

    User changeInfo(int id, String fullName, String phone, String address, String email);

}