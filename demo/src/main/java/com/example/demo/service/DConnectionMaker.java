package com.example.demo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DConnectionMaker
 */
public class DConnectionMaker implements ConnectionMaker {

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/test", "root", "p@ssw0rd");
    
        return c;
    }

	public Connection makeNewConnection() {
		return null;
	}

    
}