package com.my.taskmanagerspring.dao;

import com.my.taskmanagerspring.entity.User;
import com.my.taskmanagerspring.util.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class UserDAOImpl implements UserDAO {
    JdbcTemplate jdbcTemplate;
    UserMapper userMapper;


    public UserDAOImpl(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }

    private final String SQL_FIND_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    private final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    private final String SQL_FIND_ALL_USERS = "select * from user";
    private final String SQL_COUNT_USERS = "SELECT count(*) FROM user";


    @Override
    public User getUserById(Long id) {
        return jdbcTemplate.queryForObject(SQL_FIND_USER_BY_ID, userMapper, id);
    }

    @Override
    public User getUserByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_FIND_USER_BY_EMAIL, userMapper, email);
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return new PageImpl<>(jdbcTemplate.query(SQL_FIND_ALL_USERS, userMapper), pageable, count());
    }

    private long count() {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_COUNT_USERS, Long.class)).orElse(0L);
    }
}
