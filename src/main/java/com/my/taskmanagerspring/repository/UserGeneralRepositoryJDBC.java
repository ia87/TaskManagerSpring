package com.my.taskmanagerspring.repository;

import com.my.taskmanagerspring.entity.User;
import com.my.taskmanagerspring.repository.UserGeneralRepository;
import com.my.taskmanagerspring.util.UserMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@Qualifier("JDBCRepo")
public class UserGeneralRepositoryJDBC implements UserGeneralRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;


    public UserGeneralRepositoryJDBC(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }

    private final String SQL_FIND_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    private final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    private final String SQL_FIND_ALL_USERS = "select * from user";
    private final String SQL_COUNT_USERS = "SELECT count(*) FROM user";

    @Override
    public Page<User> findAll(Pageable pageable) {
        return new PageImpl<>(jdbcTemplate.query(SQL_FIND_ALL_USERS, userMapper), pageable, count());
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    private long count() {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_COUNT_USERS, Long.class)).orElse(0L);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
