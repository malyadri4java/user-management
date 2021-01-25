package com.dailycode.user.management.util;

public final class RequestPathUrl {
    public static final String SLASH = "/";
    public static final String DASHBOARD = "/dashboard";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String REGISTER = "/register";
    public static final String REGISTRATION_CONFIM = "/registrationConfirm";
    public static final String PASSWORD_CONFIM = "/passwordConfirm";



    public static final String REDIRECT_LOGIN_LOGOUT = "redirect:/login?logout";
    public static final String MESSAGE_FILE_PATH = "classpath:locale/messages";


    public static final String REDIRECT_USERS = "redirect:/users";
    public static final String REDIRECT_BAD_USER = "redirect:/badUser";
    public static final String REGISTRATION_CONFIM1 = SLASH + "registrationConfirm?token=%s";


    public static final String USER = "user";
    public static final String USER_ADD = "user_add";
    public static final String PROFILE = "profile";

    public static final String REGISTRATION_SUCCESS = "successRegister";
    public static final String ACCOUNT_VERIFIED = "accountVerified";



    private RequestPathUrl() {
    }

    public static String getHtmlFile(String fileName) {
        return fileName + ".html";
    }
}
