package com.example.demo.service;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * ConnectionMaker
 */
public interface ConnectionMaker {

    public Connection makeConnection() throws ClassNotFoundException, SQLException;
}