package com.icezhg.h2.controller;

import java.util.List;

import com.icezhg.h2.model.User;
import com.icezhg.h2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/list")
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/add")
    public User addUser(@ModelAttribute User user) {
        return saveUser(user);
    }

    @PostMapping(value = "/add")
    public User saveUser(@ModelAttribute User user) {
        return userRepository.save(user);
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable String id) {
        return userRepository.findOne(id);
    }

    @GetMapping("/query/{name}")
    public List<User> findByName(@PathVariable String name) {
        return userRepository.findByName(name);
    }

    @GetMapping("/find/{name}")
    public List<User> findUser(@PathVariable String name) {
        return userRepository.findUser(name);
    }

}

