package com.example.demo.service;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import com.example.demo.domain.Level;
import com.example.demo.domain.User;

import org.springframework.jdbc.datasource.DataSourceUtils;
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
        //동기화 작업 초기화
        TransactionSynchronizationManager.initSynchronization(); 
        
        //DB커넥션 생성후 이후 DAO 작업은 이 커넥션에서만 실행된다.
        Connection c = DataSourceUtils.getConnection(dataSource); 
        c.setAutoCommit(false);

        try {
            List<User> users = userDao.getAll();

            for (User user : users) {
                if (canUpgradeLevel(user)){
                    upgradeLevel(user);
                }
            }
            c.commit(); //정상적 마친 후 커밋.
        } catch (Exception e) {
            c.rollback(); //예외발생하면 롤백.
            throw e;
        } finally {
            //스프링 유틸리티 메소드로 안전하게 커넥션 닫기.
            DataSourceUtils.releaseConnection(c, dataSource); 

            //동기화 작업 종료 및 정리.
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
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