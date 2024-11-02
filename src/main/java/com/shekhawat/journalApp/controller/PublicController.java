package com.shekhawat.journalApp.controller;

import com.shekhawat.journalApp.entity.User;
import com.shekhawat.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user) {
        userService.saveNewUser(user);
    }
}
