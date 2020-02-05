package com.example.demo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.example.demo.domain.User;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDaoJdbc implements UserDao  {
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userMapper = new RowMapper<User>(){

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));

                return user;
        }
        
    } ;

    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id", this.userMapper);
    }
   


    public int getCount() throws SQLException {

        int count = jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
        return count;
    }

    public void deleteAll() throws SQLException {

        this.jdbcTemplate.execute("delete from users");
    }

    public void add(final User user) throws SQLException {
        this.jdbcTemplate.batchUpdate("insert into users(id, name, password) values(?,?,?)", new BatchPreparedStatementSetter(){
        
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
            }
        
            @Override
            public int getBatchSize() {
                return 1;
            }
        });
    }

    public User get(final String id) throws SQLException {

        return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[] {id}, this.userMapper);        
    }

    
}

