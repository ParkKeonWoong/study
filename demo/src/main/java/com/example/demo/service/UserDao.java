package com.example.demo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.example.demo.domain.User;

import org.springframework.dao.EmptyResultDataAccessException;



public class UserDao {
    private DataSource dataSource;
    private User user=null;

    private JdbcContext jdbcContext;

    public void setJdbcContext(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }
    

    

    public int getCount() throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select count(*) from users");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }


    public void deleteAll() throws SQLException {
        this.jdbcContext.workWithStatementStrategy(new StatementStrategy(){
        
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("delete from users");
                return ps;
            }
        });
        
    }

    public void add(final User user) throws SQLException {
        this.jdbcContext.workWithStatementStrategy(new StatementStrategy(){

            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
        
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
        
                return ps;
            }
        });
    }

    public User get(final String id) throws SQLException {
        ResultSet rs = null;
        Connection c = null;
        PreparedStatement ps = null;
        
        try {  
            c = dataSource.getConnection();
            ps = c.prepareStatement("select * from users where id = ?");

            ps.setString(1, id);

            rs = ps.executeQuery();

            if(rs.next()) {
                this.user = new User(rs.getString("id"),rs.getString("name"),rs.getString("password"));
            }
            if (this.user == null) throw new EmptyResultDataAccessException(1);
            return this.user;            
        } catch (SQLException e) {
            throw e;
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if(c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
            
        }
        

        

        
    }

    
}

