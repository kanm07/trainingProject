package com.codesphere.backend.controller;

import com.codesphere.backend.entity.User;
import com.codesphere.backend.security.JwtUtil;
import com.codesphere.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register User API
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // Get User by Email API
    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email).orElse(null);
    }

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        User existingUser = userService.loginUser(user.getEmail(), user.getPassword());

        if (existingUser != null) {
            return jwtUtil.generateToken(existingUser.getEmail());
        } else {
            return "Invalid credentials";
        }
    }
}