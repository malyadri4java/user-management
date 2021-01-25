package com.dailycode.user.management.controller;

import com.dailycode.user.management.constants.UserManagementConstants;
import com.dailycode.user.management.model.User;
import com.dailycode.user.management.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@Slf4j
public class NavigationController {

    @ModelAttribute
    public void user(Model model) {
        model.addAttribute(UserManagementConstants.USER, new User());
    }

    @GetMapping ("/register")
    public String displayRegistration(Model model) {
        log.info("Navigating to user registration page with {} ", ViewNames.getHtmlFile(ViewNames.REGISTER));
        return ViewNames.REGISTER;
    }

    @GetMapping ("/accountVerified")
    public String accountVerified(Model model) {
        log.info("Navigating to user verification page with {} ", ViewNames.getHtmlFile(ViewNames.ACCOUNT_VERIFIED));
        return ViewNames.ACCOUNT_VERIFIED;
    }
}
