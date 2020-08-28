package com.sidnev.springboot.controller;

import com.sidnev.springboot.model.Role;
import com.sidnev.springboot.model.User;
import com.sidnev.springboot.service.RoleService;
import com.sidnev.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/")
public class RestAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('ADMIN,USER') or hasAuthority('USER')")
    @GetMapping(value = "/user")
    public User userPage(@AuthenticationPrincipal User user) {
        return user;
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('ADMIN,USER')")
    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.allUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('ADMIN,USER')")
    @RequestMapping(value = "/users/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> saveCustomer(@RequestBody @Valid User user, Role role) {
        HttpHeaders headers = new HttpHeaders();

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (role == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.roleService.addRole(role);
        this.userService.add(user);
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('ADMIN,USER')")
    @PutMapping(value = "/users/update")
    public ResponseEntity<User> editUser(@RequestBody User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.edit(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('ADMIN,USER')")
    @RequestMapping(value = "/users/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> deleteCustomer(@PathVariable("id") Integer id) {
        User user = this.userService.getById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.userService.delete(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
