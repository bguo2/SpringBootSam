package com.example.demo.controller;

import com.example.demo.Repositories.JpaUserRepository;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/jpatest")
public class JpaTestController {

    @Autowired
    private JpaUserRepository userRepository;

    @GetMapping("/getallusers")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/adduser")
    public @ResponseBody boolean addUser(@Valid @RequestBody User user) {
        userRepository.save(user);
        return true;
    }
}
