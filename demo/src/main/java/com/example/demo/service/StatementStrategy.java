package com.example.demo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * StatementStrategy
 */
public interface StatementStrategy {
    PreparedStatement makePreparedStatement(Connection c) throws SQLException;
    
}