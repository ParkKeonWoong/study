package com.example.demo;

import com.example.demo.service.ConnectionMaker;
import com.example.demo.service.DConnectionMaker;
import com.example.demo.service.DaoFactory;
import com.example.demo.service.UserDao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import com.example.demo.domain.*;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.junit.jupiter.api.Test;

@SpringBootTest
public class UserDaoTest {

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
	UserDao dao = context.getBean("userDao", UserDao.class);
	User user = new User();
	user.setId("1234");
	user.setName("456");
	user.setPassword("married7");
	dao.add(user);
    User user2 = dao.get(user.getId());

	assertEquals(user.getName(), user2.getName());

  
    
    
        
    context.close();

	}

}
