package com.example.demo.service;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import com.example.demo.domain.Level;
import com.example.demo.domain.User;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;


/**
 * UserService
 */
public class UserService {
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    UserDao userDao;
    DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public UserDao getUserDao() {
        return this.userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    public void upgradeLevelsWithoutTransaction() {
        
            List<User> users = userDao.getAll();

            for (User user : users) {
                if (canUpgradeLevel(user)){
                    upgradeLevel(user);
                }
            }
        
    }

    public void upgradeLevels() throws Exception{
        //JDBC 트랜잭션 추상 오브젝트 생성.
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);


        //트랜잭션 시작.
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        
        try {
            List<User> users = userDao.getAll();

            for (User user : users) {
                if(canUpgradeLevel(user)){
                    upgradeLevel(user);
                }
            }
            transactionManager.commit(status);
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }

    }

    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }

    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }

	public void add(User user) {
        if (user.getLevel() == null){
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
	}

    





    
}