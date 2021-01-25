package com.dailycode.user.management.repository;

import com.dailycode.user.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);

    User findByEmailIgnoreCase(String email);

    User findByUserNameIgnoreCase(String userName);

    @Query (value = "SELECT user FROM User user WHERE user.userName!=:userName")
    public List<User> findAll(@Param ("userName") String userName);
}
