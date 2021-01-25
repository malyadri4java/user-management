package com.dailycode.user.management.config;

import com.dailycode.user.management.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity (prePostEnabled = true)
@Configuration
@Slf4j
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private Environment environment;

    @Autowired
    private WebMessageConfig messageConfig;

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/registration", "/accountVerified", "/passwordConfirm").permitAll()
                .antMatchers("/login", "/register").permitAll()
                .antMatchers("/registrationConfirm", "/login?error").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().usernameParameter("userName")
                .loginPage("/login").successHandler(successFailureHandler()).failureHandler(loginFailureHandler()).permitAll()
                .and()
                .logout().invalidateHttpSession(true).clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler(logoutSuccessHandler()).permitAll()
                .deleteCookies("remember-me").permitAll().and()
                .rememberMe().tokenValiditySeconds(180).and()
                .exceptionHandling().accessDeniedPage("/403");
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/css/**", "/js/**", "/img/**", "/webfonts/**");
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, exception) -> {
            log.info("Authentication failed, due to : {}", exception.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            request.getSession().setAttribute("error", messageConfig.getMessage("user.invalid.credentials"));
            response.sendRedirect("/login?error");
        };
    }


    private AuthenticationSuccessHandler successFailureHandler() {
        return (request, response, authentication) -> {
            log.info("Login successful!");
            //request.getSession().setAttribute("message", messageConfig.getMessage("user.login.success"));
            response.sendRedirect("/dashboard"); // environment.getProperty("server.context-path")
        };
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            log.info("Logout successful!");
            request.getSession().setAttribute("message", messageConfig.getMessage("user.logout.success"));
            response.sendRedirect("/login?logout"); // environment.getProperty("server.context-path")
        };
    }
}