package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import com.example.demo.domain.Level;
import com.example.demo.domain.User;
import com.example.demo.service.MailSender;
import com.example.demo.service.MockMailSender;
import com.example.demo.service.UserDao;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;
import com.example.demo.service.UserServiceTx;

import static com.example.demo.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static com.example.demo.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

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
    UserServiceImpl userServiceImpl;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    MailSender mailSender;

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @BeforeEach
    public void setUp() {
        users = Arrays.asList(
        new User("id1", "name1", "password1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1 , 0 ,"rjsdndv@naver.com"),
        new User("id2", "name2", "password2", Level.SILVER, MIN_RECOMMEND_FOR_GOLD+1, MIN_RECOMMEND_FOR_GOLD+1,"rjsdndv@naver.com"),
        new User("id3", "name3", "password3", Level.GOLD, MIN_LOGCOUNT_FOR_SILVER, 29,"rjsdndv@naver.com"),
        new User("id4", "name4", "password4", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER+1, MIN_RECOMMEND_FOR_GOLD+1,"rjsdndv@naver.com"),
        new User("id5", "name5", "password5", Level.SILVER, MIN_LOGCOUNT_FOR_SILVER+1, 31,"rjsdndv@naver.com"),
        new User("id6", "name6", "password6", Level.GOLD, MIN_RECOMMEND_FOR_GOLD-1, 100,"rjsdndv@naver.com")
        );
    }

    @Test
    @DirtiesContext
    public void upgradeLevels2() throws Exception {
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.GOLD);
        checkLevel(users.get(2), Level.GOLD);
        checkLevel(users.get(3), Level.SILVER);
        checkLevel(users.get(4), Level.GOLD);
        checkLevel(users.get(5), Level.GOLD);

        List<String> request = mockMailSender.getRequests();
        assertEquals(request.size(),3);
        assertEquals(request.get(0),users.get(1).getEmail());
        assertEquals(request.get(1),users.get(3).getEmail());
        

    }
    

    @Test
    public void upgradeAllOrNothing() throws Exception {
        TestUserService testUserService = new TestUserService(users.get(3).getId());
        testUserService.setUserDao(userDao);
        testUserService.setMailSender(mailSender);

        UserServiceTx txUserService = new UserServiceTx();
        txUserService.setTransactionManager(transactionManager);
        txUserService.setUserService(testUserService);

        
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        try {
            //transaction 사용
            txUserService.upgradeLevels();
            fail("TestUserServiceException expected");
            //transaction 미사용
            //testUserService.upgradeLevelsWithoutTransaction();
            
            //fail("TestUserServiceException");
        } catch (TestUserServiceException e) {
        }
        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.GOLD);
        checkLevel(users.get(3), Level.BASIC);
        checkLevel(users.get(4), Level.SILVER);
        checkLevel(users.get(5), Level.GOLD);

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
        checkLevel(users.get(1), Level.GOLD);
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