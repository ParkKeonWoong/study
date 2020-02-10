package com.example.demo.service;

import com.example.demo.domain.User;

public interface UserService {
    void add(User user);
    void upgradeLevels() throws Exception;
}
