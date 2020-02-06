package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import com.example.demo.domain.Level;
import com.example.demo.domain.User;
import com.example.demo.service.UserDao;
import com.example.demo.service.UserService;
import static com.example.demo.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static com.example.demo.service.UserService.MIN_RECOMMEND_FOR_GOLD;

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
    DataSource dataSource;

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @BeforeEach
    public void setUp() {
        users = Arrays.asList(
        new User("id1", "name1", "password1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1 , 0),
        new User("id2", "name2", "password2", Level.SILVER, MIN_RECOMMEND_FOR_GOLD+1, MIN_RECOMMEND_FOR_GOLD+1),
        new User("id3", "name3", "password3", Level.GOLD, MIN_LOGCOUNT_FOR_SILVER, 29),
        new User("id4", "name4", "password4", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER+1, MIN_RECOMMEND_FOR_GOLD+1),
        new User("id5", "name5", "password5", Level.SILVER, MIN_LOGCOUNT_FOR_SILVER+1, 31),
        new User("id6", "name6", "password6", Level.GOLD, MIN_RECOMMEND_FOR_GOLD-1, 100)
        );
    }

    @Test
    public void upgradeAllOrNothing() throws Exception {
        UserService testUserService = new TestUserService(users.get(4).getId());
        testUserService.setUserDao(this.userDao);
        testUserService.setDataSource(this.dataSource);
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        try {
            //transaction 사용
            testUserService.upgradeLevels();
            //transaction 미사용
            //testUserService.upgradeLevelsWithoutTransaction();
            
            fail("TestUserServiceException");
        } catch (TestUserServiceException e) {
        }
        checkLevel(users.get(1), Level.GOLD);
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
    public void upgradeLevels() throws Exception {
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
        checkLevel(users.get(3), Level.BASIC);
        checkLevel(users.get(4), Level.GOLD);
        checkLevel(users.get(5), Level.GOLD);


        
    }

    public void checkLevel(User user, Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertEquals(userUpdate.getLevel(),expectedLevel);
    }



    

    
}