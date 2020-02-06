package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.User;

/**
 * UserDao
 */
public interface UserDao {

    void add(User user) ;
    User get(String id) ;
    List<User> getAll();
    void deleteAll() ;
    int getCount() ;
	void update(User user);
}