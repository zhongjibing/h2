package com.icezhg.h2.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.icezhg.h2.model.User;
import com.icezhg.h2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    private UserService userService;

    @GetMapping(value = "/list")
    public List<User> listUsers(HttpServletRequest request, Sort sort) {
        return userService.findAll();
    }

    @GetMapping(value = "/add")
    public User addUser(@ModelAttribute User user) {
        return saveUser(user);
    }

    @PostMapping(value = "/add")
    public User saveUser(@ModelAttribute User user) {
        return userService.create(user);
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable String id) {
        return userService.findOne(id);
    }

    @GetMapping("/query/{name}")
    public List<User> findByName(@PathVariable String name) {
        return userService.findByName(name);
    }

    @GetMapping("/find/{name}")
    public List<User> findUser(@PathVariable String name) {
        return userService.findUser(name);
    }

}

