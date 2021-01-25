package com.dailycode.user.management.service;

import com.dailycode.user.management.encoder.BCryptPasswordEncoderUtil;
import com.dailycode.user.management.model.User;
import com.dailycode.user.management.repository.UserRepository;
import com.dailycode.user.management.util.TimeUtil;
import com.dailycode.user.management.util.UserServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository repository;

    public User createUser(User user) {
        if (user.getPassword() != null && user.getPassword().trim().length() > 0) {
            user.setPassword(BCryptPasswordEncoderUtil.encode(user.getPassword()));
        }
        user.setCreationDate(TimeUtil.dateAndTimeAsString());
        repository.save(user);
        return user;
    }

    public List<User> listUsers() {
        String currentUserName = UserServiceUtil.getCurrentUserName();
        if (currentUserName != null && currentUserName.trim().length() > 0) {
            return repository.findAll(currentUserName);
        }
        return repository.findAll();
    }

    public User findByUserName(String name) {
        return repository.findByUserName(name);
    }

    public User findByUserId(Long userId) {
        return repository.findById(userId).orElse(null);
    }

    public User updateUser(User user) {
        return repository.save(user);
    }

    public void deleteUser(User user) {
        if (Objects.nonNull(user) && user.getUserId() > 0) {
            repository.delete(user);
        }
    }

    public User findByEmailIgnoreCase(String emailId) {
        return repository.findByEmailIgnoreCase(emailId);
    }
}
