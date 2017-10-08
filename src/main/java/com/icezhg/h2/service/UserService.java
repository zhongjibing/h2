package com.icezhg.h2.service;

import java.util.List;
import javax.annotation.Resource;
import javax.validation.constraints.Null;

import com.icezhg.h2.model.User;
import com.icezhg.h2.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserService:
 *
 * @author zhongjibing 2017-10-08
 * @version 1.0
 */
@Service
public class UserService {

    @Resource
    private UserRepository userRepository;


    public List<User> findUser(String name) {
        return userRepository.findUser(name);
    }

    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public User findOne(String id) {
        return userRepository.findOne(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

//    @Transactional
    public User create(User user) {
        User saved = userRepository.save(user);
        if (user.getId() != null) {
            throw new NullPointerException();
        }
        return saved;
    }
}
