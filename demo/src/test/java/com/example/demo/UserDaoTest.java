package com.example.demo;

import com.example.demo.service.UserDao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;


import com.example.demo.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserDaoTest {


	@Autowired
	@Qualifier("userDao")
	private UserDao dao;


	@BeforeEach
	public void setUp(GenericApplicationContext ctx){
		
		System.out.println("this is context = " + ctx.getBean("userDao"));
		System.out.println(this.dao);
		System.out.println(this);
	}

	@Test
	public void duplicateKey() {
		Exception exception = assertThrows(DataAccessException.class, () -> {
			dao.deleteAll();
		User user = new User("1234", "name", "password");
		dao.add(user);
		dao.add(user);
		}); 
		
		System.out.println(exception.getMessage());
		assertTrue(exception.getMessage().contains("Duplicate entry"));

		
	}

    @Test
    public void getUserFailure() throws SQLException {
	
		dao.deleteAll();
		User user = new User("1234","name","password");
		dao.add(user);
		
		dao.deleteAll();
		
		dao.deleteAll();
		assertThrows(EmptyResultDataAccessException.class, () ->
		dao.get("654"));

	}
	@Test
	void testExpectedException() {
	
		assertThrows(NumberFormatException.class, () -> {
		Integer.parseInt("One");
	});
		
	}


	@Test
    public void addAndGet() throws SQLException, ClassNotFoundException{
   
	// dao.deleteAll();
	assertEquals(dao.getCount(),0);
	User user = new User("4567","name","password");
	dao.add(user);
	// assertEquals(dao.getCount(),1);
	User user2 = dao.get(user.getId());
	System.out.println(user.getId()+ user2.getId());
	System.out.println(user.getName()+ user2.getName());
	System.out.println(user.getPassword()+ user2.getPassword());

	// assertEquals(user.getName(), user2.getName());
	// assertEquals(user.getPassword(), user2.getPassword());

	}

	@Test
    public void count() throws SQLException {
		
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
		
	}

}
