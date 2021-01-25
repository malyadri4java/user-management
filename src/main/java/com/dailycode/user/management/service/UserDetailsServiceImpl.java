package com.dailycode.user.management.service;

import com.dailycode.user.management.model.User;
import com.dailycode.user.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private String roleStr = "ROLE_%s";

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User userInfo = repository.findByUserName(userName);
        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User.builder().username(userInfo.getUserName())
                .password(userInfo.getPassword())
                .roles(userInfo.getRole().getRoleName())
                .disabled(false)
                .build();
    }

    private GrantedAuthority getAuthority(String role) {
        return new SimpleGrantedAuthority(String.format(roleStr, role.toUpperCase()));
    }

}
