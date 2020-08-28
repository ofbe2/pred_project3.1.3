package com.sidnev.springboot.dao;

import com.sidnev.springboot.model.Role;

import java.util.List;

public interface RoleDAO {
    void addRole(Role role);

    void deleteRole(Role role);

    void updateRole(Role role);

    Role getRoleById(int id);

    List<Role> getAllRoles();

    Role getRoleByName(String name);
}