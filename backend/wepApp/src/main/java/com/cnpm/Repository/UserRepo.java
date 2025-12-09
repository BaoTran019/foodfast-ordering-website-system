package com.cnpm.Repository;

import com.cnpm.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUserId(int userId);

    // @Query("SELECT u FROM Users u WHERE u.fullName = :fullName")
    Optional<User> findByFullName(@Param("fullName") String fullName);

    // @Query("SELECT u FROM Users u WHERE u.phone = :phone")
    Optional<User> findByPhone(@Param("phone") String phone);

}
