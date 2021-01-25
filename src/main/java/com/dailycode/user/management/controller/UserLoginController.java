package com.dailycode.user.management.controller;

import com.dailycode.user.management.config.WebMessageConfig;
import com.dailycode.user.management.constants.MessageConstants;
import com.dailycode.user.management.constants.UserManagementConstants;
import com.dailycode.user.management.util.RequestPathUrl;
import com.dailycode.user.management.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class UserLoginController {

    @Autowired
    private WebMessageConfig messageConfig;

    @RequestMapping (path = RequestPathUrl.LOGIN, method = {RequestMethod.GET, RequestMethod.POST})
    public String loginPage(@RequestParam (value = "error", required = false) String error,
                            @RequestParam (value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute(UserManagementConstants.ERROR, messageConfig.getMessage(MessageConstants.USER_INVALID));
        }
        if (logout != null) {
            model.addAttribute(UserManagementConstants.MSG, messageConfig.getMessage(MessageConstants.USER_LOGOUT));
        }
        return ViewNames.LOGIN;
    }

    @GetMapping (RequestPathUrl.LOGOUT)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return RequestPathUrl.REDIRECT_LOGIN_LOGOUT;
    }
}
