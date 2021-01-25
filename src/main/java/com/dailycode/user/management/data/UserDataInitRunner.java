package com.dailycode.user.management.data;

import com.dailycode.user.management.model.User;
import com.dailycode.user.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDataInitRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        //User user = new User(null, "admin", "admin", "cue@gmail.com", "malya", "naidu", "Cue Mobiles Inc.", 223344L, "chennai", "chennai", "ADMIN", "Active", null);
        //userService.createUser(user);
    }
}
