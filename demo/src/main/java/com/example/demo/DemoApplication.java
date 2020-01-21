package com.example.demo;

import com.example.demo.service.UserDao;

import java.sql.SQLException;

import com.example.demo.domain.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		SpringApplication.run(DemoApplication.class, args);

	UserDao dao = new UserDao();
	User user = new User();
	user.setId("whiteship2");
	user.setName("back2");
	user.setPassword("married2");
	dao.add(user);
	System.out.println(user.getId() + " 등록 성공 ");
	User user2 = dao.get(user.getId());
	System.out.println(user2.getName());
	System.out.println(user2.getPassword());
	System.out.println(user2.getId() + " 조회 성공 ");
		System.out.println( " 조회 성공 ");

	}

}
