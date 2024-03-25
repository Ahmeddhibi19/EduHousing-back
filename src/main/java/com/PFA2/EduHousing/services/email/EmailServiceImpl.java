package com.PFA2.EduHousing.services.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService{
    private JavaMailSender javaMailSender;
    @Autowired
    EmailServiceImpl(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    @Async
    @Override
    public void sendEmail(SimpleMailMessage email){
        javaMailSender.send(email);
    }
}
