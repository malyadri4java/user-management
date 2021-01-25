package com.dailycode.user.management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class WebMessageConfig {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, getLocale());
    }

    public String getMessage(String key, String arg) {
        return messageSource.getMessage(key, new String[]{arg}, getLocale());
    }

    public String getMessage(String key, String[] strArray) {
        return messageSource.getMessage(key, strArray, getLocale());
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}