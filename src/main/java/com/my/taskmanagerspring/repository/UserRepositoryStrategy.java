package com.my.taskmanagerspring.repository;

import com.my.taskmanagerspring.entity.User;
import com.my.taskmanagerspring.interceptor.RequestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class UserRepositoryStrategy implements UserGeneralRepository{

    public static final String JDBC = "jdbc";
    private final UserGeneralRepository JPARepo;
    private final UserGeneralRepository JDBCRepo;
    @Autowired
    private RequestData requestData;

    public UserRepositoryStrategy(@Qualifier("JPARepo") UserGeneralRepository JPARepo,
                                  @Qualifier("JDBCRepo") UserGeneralRepository JDBCRepo) {
        this.JPARepo = JPARepo;
        this.JDBCRepo = JDBCRepo;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        String repositorySwitch = requestData.getRepositorySwitch();
        if (JDBC.equals(repositorySwitch)) {
            return JDBCRepo.findAll(pageable);
        } else {
            return JPARepo.findAll(pageable);
        }

    }

    @Override
    public Optional<User> findByEmail(String email) {
        return JPARepo.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
