package com.example.demo;

import com.example.demo.service.UserDao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.example.demo.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserDaoTest {

	User user1;
	User user2;
	User user3;

	@Autowired
	@Qualifier("userDao")
	private UserDao dao;

	@Autowired
	DataSource dataSource;


	@BeforeEach
	public void setUp(GenericApplicationContext ctx){
		this.user1 = new User("gumme1","Park1","springno1" ,Level.BASIC, 50 ,0 );
		this.user2 = new User("gumme2","Park2","springno2",Level.SILVER, 55,10 );
		this.user3 = new User("gumme3","Park3","springno3",Level.GOLD, 100,40);
	}

	
    
	
	@Test
	public void duplicateKey() {
		assertThrows(DataAccessException.class, () -> {
			dao.deleteAll();
		User user = this.user1;
		dao.add(user);
		dao.add(user);
		}); 
		
		dao.deleteAll();


		
	}

    @Test
    public void getUserFailure() throws SQLException {
	
		dao.deleteAll();
		User user = this.user1;
		dao.add(user);
		

		System.out.println(dao.get("gumme1").getId());

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
	User user = this.user1;
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
		checkSameUser(user1, users3.get(0));
		checkSameUser(user2, users3.get(1));
		checkSameUser(user3, users3.get(2));

		List<User> users4 = dao.getAll();

		System.out.println(users4.get(0).getId());
		System.out.println(users4.get(1).getId());
		System.out.println(users4.get(2).getId());




		
	}

	private void checkSameUser(User user1, User user2) {
		assertEquals(user1.getId(), user2.getId());
		assertEquals(user1.getName(), user2.getName());
		assertEquals(user1.getPassword(), user2.getPassword());
		assertEquals(user1.getLevel(), user2.getLevel());
		assertEquals(user1.getLogin(), user2.getLogin());
		assertEquals(user1.getRecommend(), user2.getRecommend());
	}

}
