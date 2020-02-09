package com.example.demo.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

/**
 * MailSender
 */
public interface MailSender {

    void send(SimpleMailMessage simpleMessage) throws MailException;
    void send(SimpleMailMessage[] simpleMessage) throws MailException;
}