package com.example.demo;

import com.example.demo.service.ConnectionMaker;
import com.example.demo.service.DConnectionMaker;
import com.example.demo.service.DaoFactory;
import com.example.demo.service.UserDao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import com.example.demo.domain.*;

import org.apache.catalina.core.ApplicationContext;
import org.assertj.core.api.Assertions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.junit.jupiter.api.Test;




@SpringBootTest
public class UserDaoTest {

    @Test
    public void getUserFailure() throws SQLException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		
		UserDao dao = context.getBean("userDao",UserDao.class);

		dao.deleteAll();
		User user = new User("1234","name","password");
		dao.add(user);
		
		dao.deleteAll();
		
		dao.deleteAll();
		assertThrows(EmptyResultDataAccessException.class, () ->
		dao.get("654"));

		context.close();
	}
	@Test
	void testExpectedException() {
	
		assertThrows(NumberFormatException.class, () -> {
		Integer.parseInt("One");
	});
		
	}


	@Test
    public void addAndGet() throws SQLException, ClassNotFoundException{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
	UserDao dao = context.getBean("userDao", UserDao.class);
	dao.deleteAll();
	assertEquals(dao.getCount(),0);
	User user = new User("1234","name","password");
	dao.add(user);
	assertEquals(dao.getCount(),1);
    User user2 = dao.get(user.getId());

	assertEquals(user.getName(), user2.getName());
	assertEquals(user.getPassword(), user2.getPassword());

    context.close();

	}

	@Test
    public void count() throws SQLException {
	
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user1 = new User("gumme","Park1","springno1");
		User user2 = new User("gumme2","Park2","springno2");
		User user3 = new User("gumme3","Park3","springno3");
		
		dao.deleteAll();
		assertEquals(dao.getCount(),0);

		dao.add(user1);
		assertEquals(dao.getCount(),1);
		dao.add(user2);
		assertEquals(dao.getCount(),2);
		dao.add(user3);
		assertEquals(dao.getCount(),3);
			
		context.close();

	}

}
