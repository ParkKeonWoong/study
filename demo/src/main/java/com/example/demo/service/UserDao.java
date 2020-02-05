package com.example.demo.service;

import java.sql.SQLException;
import java.util.List;

import com.example.demo.domain.User;

/**
 * UserDao
 */
public interface UserDao {

    void add(User user) throws SQLException;
    User get(String id) throws SQLException;
    List<User> getAll();
    void deleteAll() throws SQLException;
    int getCount() throws SQLException;
}