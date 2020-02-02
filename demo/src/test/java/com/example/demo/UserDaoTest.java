package com.example.demo;

import com.example.demo.service.UserDao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.List;

import com.example.demo.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
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
    public void getUserFailure() throws SQLException {
	
		dao.deleteAll();
		User user = new User("1234","name","password");
		dao.add(user);
		

		System.out.println(dao.get("1234").getId());

	}
	@Test
	void testExpectedException() {
	
		assertThrows(NumberFormatException.class, () -> {
		Integer.parseInt("One");
	});
		
	}


	@Test
    public void addAndGet() throws SQLException, ClassNotFoundException{
   
	dao.deleteAll();
	assertEquals(dao.getCount(),0);
	User user = new User("4567","name","password");
	dao.add(user);
	// assertEquals(dao.getCount(),1);
	dao.deleteAll();
	// User user2 = dao.get(user.getId());
	List<User> users = dao.getAll();
	System.out.println(users.size());
	// System.out.println(user.getId()+ user2.getId());
	// System.out.println(user.getName()+ user2.getName());
	// System.out.println(user.getPassword()+ user2.getPassword());

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

	@Test
    public void getAll() throws SQLException {
		
		User user1 = new User("gyumme","Park1","springno1");
		User user2 = new User("leegw700","Park2","springno2");
		User user3 = new User("bumjin","Park3","springno3");
		
		dao.deleteAll();

		dao.add(user1);
		List<User> users1 = dao.getAll();
		assertEquals(users1.size(), 1);
		checkSameUser(user1, users1.get(0));

		dao.add(user2);
		List<User> users2 = dao.getAll();
		assertEquals(users2.size(), 2);
		checkSameUser(user1, users2.get(0));
		checkSameUser(user2, users2.get(1));
		dao.add(user3);
		List<User> users3 = dao.getAll();
		assertEquals(users3.size(), 3);
		checkSameUser(user3, users3.get(0));
		checkSameUser(user1, users3.get(1));
		checkSameUser(user2, users3.get(2));

		List<User> users4 = dao.getAll();

		System.out.println(users4.get(0).getId());
		System.out.println(users4.get(1).getId());
		System.out.println(users4.get(2).getId());




		
	}

	private void checkSameUser(User user1, User user2) {
		assertEquals(user1.getId(), user2.getId());
		assertEquals(user1.getName(), user2.getName());
		assertEquals(user1.getPassword(), user2.getPassword());
	}

}
