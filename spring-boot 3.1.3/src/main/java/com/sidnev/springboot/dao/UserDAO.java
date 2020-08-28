package com.sidnev.springboot.dao;


import com.sidnev.springboot.model.User;

import java.util.List;

public interface UserDAO {
    List<User> allUsers();
    User findByName(String name);
    void add(User user);
    void delete(User user);
    void edit(User user);
    User getById(int id);
}
