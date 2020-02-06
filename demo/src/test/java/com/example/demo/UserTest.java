package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
public class UserTest {

    User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void upgradeLevel() {
        Level[] levels = Level.values();
        for (Level level : levels) {
            if (level.nextLevel() == null) continue;
            user.setLevel(level);
            user.upgradeLevel();
            assertEquals(user.getLevel(), level.nextLevel());
            System.out.println(level);
        }
    }

    @Test
    public void cannotUpgradeLevel() {
        assertThrows(IllegalArgumentException.class, () -> {
        Level[] levels= Level.values();
        for (Level level : levels) {
            if (level.nextLevel() != null) continue;
            user.setLevel(level);
            user.upgradeLevel();
        }
        });
    }
    
    

    
}