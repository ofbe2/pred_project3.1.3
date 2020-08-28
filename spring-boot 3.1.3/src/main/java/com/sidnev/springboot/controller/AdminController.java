package com.sidnev.springboot.controller;

import com.sidnev.springboot.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "redirect:/login";
    }

    @PreAuthorize(value = "hasAuthority('ADMIN') or hasAuthority('ADMIN,USER')")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String listUsers(@AuthenticationPrincipal User authUser, Model model) {
        model.addAttribute("authUser", authUser);
        return "users";
    }
}
