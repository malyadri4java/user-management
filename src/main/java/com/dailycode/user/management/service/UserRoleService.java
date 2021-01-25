package com.dailycode.user.management.service;

import com.dailycode.user.management.constants.RolesEnum;
import com.dailycode.user.management.model.Role;
import com.dailycode.user.management.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserRoleService {

    @Autowired
    private RoleRepository repository;

    public Role createUserRole(Role role) {
        return repository.save(role);
    }

    public List<Role> listUserRoles() {
        return repository.findAll();
    }

    public Role getDefaultUserRole() {
        return repository.findByRoleName(RolesEnum.ENGINEER.value());
    }
}
