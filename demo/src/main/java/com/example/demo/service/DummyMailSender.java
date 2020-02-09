package com.example.demo.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

/**
 * DummyMailSender
 */
public class DummyMailSender implements MailSender {
    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        // TODO Auto-generated method stub
        
    }
    public void send(SimpleMailMessage[] simpleMessage) throws MailException {
        // TODO Auto-generated method stub
        
    }
    
}