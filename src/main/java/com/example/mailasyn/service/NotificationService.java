package com.example.mailasyn.service;

import com.example.mailasyn.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by : maru
 * Date  : 1/21/2020
 * Time  : 10:51 AM
 */

@Service
public class NotificationService {

    private JavaMailSender javaMailSender;
    private Logger logger = LoggerFactory.getLogger(NotificationService.class);
    @Autowired
    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public static int noOfQuickServiceThreads = 20;

    private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);

    @Async
    public void sendNotificaitoin(User user) throws InterruptedException {
        System.out.println("Sleeping now....");

        Thread.sleep(1000);

        System.out.println("Sending email...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmailAddress());
        mail.setFrom("sai.vichet70@gmail.com");
        mail.setSubject("Spring Boot Asyn");
        mail.setText("why so bobo and noob");
        quickService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    javaMailSender.send(mail);
                }catch (Exception e) {
                    logger.error("Exception occur while send a mail : ",e);
                }
            }
        });


        System.out.println("Email send!");
    }
}
