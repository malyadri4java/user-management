package com.dailycode.user.management.controller;

import com.dailycode.user.management.constants.UserManagementConstants;
import com.dailycode.user.management.model.User;
import com.dailycode.user.management.service.UserRoleService;
import com.dailycode.user.management.service.UserService;
import com.dailycode.user.management.util.UserServiceUtil;
import com.dailycode.user.management.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping ("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService roleService;

    @GetMapping
    public String getAllUsers(Model model) {
        log.info("Listing all users!");
        List<User> users = userService.listUsers();
        if (users.isEmpty()) {
            log.info("User(s) are empty!");
        }
        model.addAttribute("users", users);
        model.addAttribute("page_headline", "User Management");
        log.info("User details are loading with {}", ViewNames.getHtmlFile(ViewNames.USER));
        return ViewNames.USER;
    }

    @GetMapping ("/add")
    public String addUser(Model model) {
        model.addAttribute(UserManagementConstants.USER, new User());
        model.addAttribute(UserManagementConstants.ROLES, roleService.listUserRoles());
        log.info("Navigating to user creation page with {} ", ViewNames.getHtmlFile(ViewNames.USER_ADD));
        return ViewNames.USER_ADD;
    }

    @GetMapping ("/profile/{userId}")
    public String getProfile(@PathVariable String userId, Model model) {
        log.info("Getting profile details with user id :{}", userId);
        User user = userService.findByUserName(userId);
        if (user == null) {
            user = userService.findByUserId(Long.valueOf(userId));
        }
        model.addAttribute(UserManagementConstants.USER, user);
        model.addAttribute(UserManagementConstants.ROLES, roleService.listUserRoles());
        model.addAttribute("page_headline", "Edit User Profile");
        log.info("User profile details are loading with {}", ViewNames.getHtmlFile(ViewNames.PROFILE));
        return ViewNames.PROFILE;
    }

    @GetMapping ("/view/{userId}")
    public String viewProfile(@PathVariable String userId, Model model) {
        log.info("Getting profile view details with user id :{}", userId);
        User user = userService.findByUserName(userId);
        if (user == null) {
            user = userService.findByUserId(Long.valueOf(userId));
        }
        model.addAttribute(UserManagementConstants.USER, user);
        model.addAttribute("page_headline", "View User Profile");
        log.info("User view profile details are loading with {}", ViewNames.getHtmlFile(ViewNames.PROFILE_VIEW));
        return ViewNames.PROFILE_VIEW;
    }

    @PostMapping ("/profile/{userId}")
    public String updateProfile(@Valid @ModelAttribute ("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(UserManagementConstants.USER, user);
            model.addAttribute(UserManagementConstants.ROLES, roleService.listUserRoles());
            return ViewNames.PROFILE;
        }
        log.info("Updating profile with user id :{}", user.getUserId());
        User existingUser = userService.findByUserId(user.getUserId());
        if (existingUser != null) {
            user.setUserId(existingUser.getUserId());
            userService.updateUser(user);
        }
        log.info("User was updated and redirecting into user management!");
        return ViewNames.REDIRECT_USERS;
    }

    @PostMapping ("/profile")
    public String addUserProfile(User user) {
        log.info("User is creating...");
        userService.createUser(user);
        log.info("User was created and redirecting into user management!");
        return ViewNames.REDIRECT_USERS;
    }

    @PostMapping ("/delete/{userId}")
    public String deleteUser(@PathVariable long userId) {
        log.info("Deleting user with user id :{}", userId);
        String currentUserName = UserServiceUtil.getCurrentUserName();
        User user = userService.findByUserId(userId);
        if (user == null) {
            log.error("User does not exist with userId {}", userId);
        } else if (user.getUserName().equals(currentUserName)) {
            log.info("Logged in user does not allow to delete : {}", currentUserName);
            return ViewNames.REDIRECT_USERS;
        } else {
            userService.deleteUser(user);
        }
        log.info("User was updated and redirecting into user management!");
        return ViewNames.REDIRECT_USERS;
    }
}
