package com.example.demo;

import com.example.demo.domain.User;
import com.example.demo.service.UserServiceImpl;

/**
 * TestUserService
 */
public class TestUserService extends UserServiceImpl {
    private String id;

    TestUserService(String id) {
        this.id = id;
    }

    protected void upgradeLevel(User user) {
        if (user.getId().equals(this.id)) throw new TestUserServiceException();
        super.upgradeLevel(user);
    }
    
    
}