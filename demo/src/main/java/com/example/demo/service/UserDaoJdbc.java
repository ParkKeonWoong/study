package com.example.demo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.example.demo.domain.User;
import com.example.demo.domain.Level;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDaoJdbc implements UserDao {
    private DataSource dataSource;
    private User user = null;

    private JdbcContext jdbcContext;

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcContext = new JdbcContext();
        this.jdbcContext.setDataSource(dataSource);
    }

    private RowMapper<User> userMapper = new RowMapper<User>() {

        @Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setLevel(Level.valueOf(rs.getInt("level")));
                user.setLogin(rs.getInt("login"));
                user.setRecommend(rs.getInt("recommend"));
                return user;
			}
    
    };

    public int getCount(){

        int count = jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
        return count;
    }


    public void deleteAll(){
        this.jdbcTemplate.execute("delete from users");
    }

    public void add(final User user) {
        this.jdbcTemplate.batchUpdate("insert into users(id, name, password, level, login, recommend) values(?,?,?,?,?,?)", new BatchPreparedStatementSetter(){
        
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
                ps.setInt(4, user.getLevel().intValue());
                ps.setInt(5, user.getLogin());
                ps.setInt(6, user.getRecommend());
            }
        
            @Override
            public int getBatchSize() {
                return 1;
            }
        });

    }

    public User get(String id){
        return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id}, this.userMapper);

    }   

    @Override   
    public List<User> getAll() {    
        return this.jdbcTemplate.query("select * from users order by id", this.userMapper);
}   


}   

