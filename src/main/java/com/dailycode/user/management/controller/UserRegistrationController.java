package com.dailycode.user.management.controller;

import com.dailycode.user.management.config.WebMessageConfig;
import com.dailycode.user.management.constants.StatusEnum;
import com.dailycode.user.management.constants.UserManagementConstants;
import com.dailycode.user.management.encoder.BCryptPasswordEncoderUtil;
import com.dailycode.user.management.event.OnRegistrationCompleteEvent;
import com.dailycode.user.management.model.ConfirmationToken;
import com.dailycode.user.management.model.Role;
import com.dailycode.user.management.model.User;
import com.dailycode.user.management.service.ConfirmationTokenService;
import com.dailycode.user.management.service.UserRoleService;
import com.dailycode.user.management.service.UserService;
import com.dailycode.user.management.util.RequestPathUrl;
import com.dailycode.user.management.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Controller
@Slf4j
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private WebMessageConfig messageConfig;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private UserRoleService roleService;

    @PostMapping (RequestPathUrl.REGISTER)
    public String registerUser(@Valid @ModelAttribute ("user") User user, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            return ViewNames.REGISTER;
        }
        log.info("Processing user registration...");
        if (!isValid(user.getEmail())) {
            model.addAttribute(UserManagementConstants.ERROR, messageConfig.getMessage("user.email.does.exist", user.getEmail()));
            model.addAttribute(UserManagementConstants.USER, user);
            return ViewNames.REGISTER;
        }
        User existingUser = userService.findByEmailIgnoreCase(user.getEmail());
        if (existingUser != null) {
            model.addAttribute(UserManagementConstants.MSG, messageConfig.getMessage("user.email.exist", user.getEmail()));
            user.setEmail("");// reset
            model.addAttribute(UserManagementConstants.USER, user);
            return ViewNames.REGISTER;
        }
        return createUser(user, model, request.getContextPath(), request.getLocale());
    }

    private boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    private String createUser(User user, Model model, String contextPath, Locale locale) {
        try {
            Role defaultUserRole = roleService.getDefaultUserRole();
            user.setRole(defaultUserRole);
            user = userService.createUser(user);
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, locale, contextPath));
            model.addAttribute("confirmationMessage", "A confirmation e-mail has been sent to " + user.getEmail());
            return ViewNames.REGISTRATION_SUCCESS;
        } catch (RuntimeException ex) {
            log.error("Fail to register user : {}", ex.getMessage());
            model.addAttribute(UserManagementConstants.ERROR, ex.getMessage());
            model.addAttribute(UserManagementConstants.USER, user);
            userService.deleteUser(user);
        }
        return ViewNames.REGISTER;
    }

    @RequestMapping (value = RequestPathUrl.REGISTRATION_CONFIM, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(WebRequest request, ModelAndView modelAndView, @RequestParam ("token") String confirmationToken) {
        modelAndView.setViewName(ViewNames.REDIRECT_BAD_USER);
        ConfirmationToken verificationToken = confirmationTokenService.getVerificationToken(confirmationToken);
        if (verificationToken == null) {
            String message = messageConfig.getMessage("auth.message.invalidToken");
            modelAndView.addObject(UserManagementConstants.MSG, message);
            return modelAndView;
        }

        User user = verificationToken.getUser();//verificationToken.getUser();
        if (verificationToken.isExpiryDate()) {
            String messageValue = messageConfig.getMessage("auth.message.expired");
            modelAndView.addObject(UserManagementConstants.MSG, messageValue);
            return modelAndView;
        }
        user.setStatus(StatusEnum.ACTIVE.value());
        userService.updateUser(user);
        modelAndView.addObject("confirmationToken", verificationToken.getConfirmationToken());
        modelAndView.setViewName(ViewNames.PASSWORD_CONFIM);
        return modelAndView;
    }

    @RequestMapping (value = RequestPathUrl.PASSWORD_CONFIM, method = {RequestMethod.POST, RequestMethod.GET})
    public String passwordConfirm(Model model, @RequestParam String password, @RequestParam String token) {
        ConfirmationToken verificationToken = confirmationTokenService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messageConfig.getMessage("auth.message.invalidToken");
            model.addAttribute(UserManagementConstants.MSG, message);
            return ViewNames.REDIRECT_BAD_USER;
        } else {
            User user = verificationToken.getUser();
            user.setPassword(BCryptPasswordEncoderUtil.encode(password));
            userService.updateUser(user);
            model.addAttribute(UserManagementConstants.SUCCESS_MSG, "Your password has been set!");
            return ViewNames.ACCOUNT_VERIFIED;
        }
    }
}