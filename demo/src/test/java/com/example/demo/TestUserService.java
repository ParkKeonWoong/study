package com.example.demo;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;

/**
 * TestUserService
 */
public class TestUserService extends UserService {
    private String id;

    TestUserService(String id) {
        this.id = id;
    }

    protected void upgradeLevel(User user) {
        if (user.getId().equals(this.id)) throw new TestUserServiceException();
        super.upgradeLevel(user);
    }
    
    
}