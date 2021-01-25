package com.dailycode.user.management.config;

import com.dailycode.user.management.util.RequestPathUrl;
import com.dailycode.user.management.util.ViewNames;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController(RequestPathUrl.SLASH, RequestPathUrl.DASHBOARD);
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(RequestPathUrl.MESSAGE_FILE_PATH);
        messageSource.setCacheSeconds(3600); //refresh cache once per hour
        return messageSource;
    }
}
