package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import com.example.demo.domain.Level;
import com.example.demo.domain.User;
import com.example.demo.service.UserDao;
import com.example.demo.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * UserServiceTest
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserServiceTest {

    List<User> users;

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @BeforeEach
    public void setUp() {
        users = Arrays.asList(
        new User("id1", "name1", "password1", Level.BASIC, 4, 0),
        new User("id2", "name2", "password2", Level.SILVER, 50, 10),
        new User("id3", "name3", "password3", Level.GOLD, 60, 29),
        new User("id4", "name4", "password4", Level.BASIC, 60, 30),
        new User("id5", "name5", "password5", Level.SILVER, 100, 31),
        new User("id6", "name6", "password6", Level.GOLD, 50, 100)
        );
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(5);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRoad = userDao.get(userWithLevel.getId());
        User userWithoutLevelRoad = userDao.get(userWithoutLevel.getId());

        assertEquals(userWithLevelRoad.getLevel(), userWithLevel.getLevel());
        assertEquals(userWithoutLevelRoad.getLevel(), userWithoutLevel.getLevel());
    }

    @Test
    public void upgradeLevels() {
        userDao.deleteAll();

        for (User user : users) {
            userDao.add(user);
        }

        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.GOLD);
        checkLevel(users.get(3), Level.BASIC);
        checkLevel(users.get(4), Level.SILVER);
        checkLevel(users.get(5), Level.GOLD);

        userService.upgradeLevels();

        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.GOLD);
        checkLevel(users.get(3), Level.SILVER);
        checkLevel(users.get(4), Level.GOLD);
        checkLevel(users.get(5), Level.GOLD);


        
    }

    public void checkLevel(User user, Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertEquals(userUpdate.getLevel(),expectedLevel);
    }



    

    
}