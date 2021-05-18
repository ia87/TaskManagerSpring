package com.my.taskmanagerspring.dao;

import com.my.taskmanagerspring.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDAO {
    User getUserById(Long id);
    User getUserByEmail(String email);
    Page<User> getAllUsers(Pageable pageable);

}
