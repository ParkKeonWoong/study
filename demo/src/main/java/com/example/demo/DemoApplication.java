package com.example.demo;

import com.example.demo.service.CountingConnectionMaker;
import com.example.demo.service.DaoFactory;
import com.example.demo.service.UserDao;

import java.sql.SQLException;

import com.example.demo.domain.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException{

		 DaoFactory factory = new DaoFactory();
		 UserDao dao1 = factory.userDao();
		 UserDao dao2 = factory.userDao();
		 UserDao dao5 = factory.userDao();

	 	System.out.println(dao1);
		 System.out.println(dao2);

		 AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		 UserDao dao = context.getBean("userDao",UserDao.class);
		 UserDao dao3 = context.getBean("userDao",UserDao.class);
		 UserDao dao4 = context.getBean("userDao",UserDao.class);

		 System.out.println(dao3);
		 System.out.println(dao4);

		 User user = new User();
		 user.setId("whitesh2");
		 user.setName("back9");
		 user.setPassword("married9");
		 dao.add(user);
		 System.out.println(user.getId() + " 등록 성공 ");
		 User user2 = dao.get(user.getId());
		 System.out.println(user2.getName());
		 System.out.println(user2.getPassword());
		 System.out.println(user2.getId() + " 조회 성공 ");
		System.out.println( " 조회 성공 ");
	 	CountingConnectionMaker ccm = context.getBean("connectionMaker",CountingConnectionMaker.class);
		 System.out.print("Counting = "+ccm.getCounter());
	 	context.close();
	}

}
