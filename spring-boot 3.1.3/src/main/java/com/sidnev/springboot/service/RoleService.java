package com.sidnev.springboot.service;

import com.sidnev.springboot.model.Role;

import java.util.List;

public interface RoleService {
    void addRole(Role role);

    void deleteRole(Role role);

    void updateRole(Role role);

    Role getRoleById(int id);

    List<Role> getAllRoles();

    Role getRoleByName(String name);
}