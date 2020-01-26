package com.example.demo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;


import com.example.demo.domain.User;

public class UserDao {

    private DataSource dataSource;

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    

    

  

    public void add(final User user) throws ClassNotFoundException, SQLException {
       
        final Connection c = dataSource.getConnection();
        final PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");

        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(final String id) throws ClassNotFoundException, SQLException {
       
        final Connection c = dataSource.getConnection();
        final PreparedStatement ps = c.prepareStatement("select * from users where id = ?");

        ps.setString(1, id);

        final ResultSet rs = ps.executeQuery();

        rs.next();
        final User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close(); 
        
        return user;
    }

    
}

