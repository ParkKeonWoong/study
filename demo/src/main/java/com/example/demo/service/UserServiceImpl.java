package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import com.example.demo.domain.Level;
import com.example.demo.domain.User;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;


/**
 * UserService
 */
public class UserServiceImpl implements UserService {
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    UserDao userDao;
    DataSource dataSource;
    PlatformTransactionManager transactionManager;
    private MailSender mailSender;


    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

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

    public void upgradeLevels(){
        List<User> users = userDao.getAll();
        for (User user : users) {
            if(canUpgradeLevel(user)){
                upgradeLevel(user);
            }
        }
    }

    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("useradmin@ksug.org");
        mailMessage.setSubject("upgrade 안내");
        mailMessage.setText("사용자님의 등급이 " + user.getLevel().name());

        this.mailSender.send(mailMessage);
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