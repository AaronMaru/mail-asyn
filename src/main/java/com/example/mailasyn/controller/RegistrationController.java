package com.example.mailasyn.controller;

import com.example.mailasyn.model.User;
import com.example.mailasyn.service.NotificationService;
import org.apache.tomcat.util.http.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by : maru
 * Date  : 1/21/2020
 * Time  : 10:49 AM
 */

@RestController
@RequestMapping("/api/v1")
public class RegistrationController {

    private Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/signup-success")
    public ResponseEntity signupSuccess() {
        User user = new User();
        user.setFirstName("aaron");
        user.setLastName("maru");
        user.setEmailAddress("saivichet@skybooking.info");

        logger.debug("inside sendAsynchronousMail api");
        try {
            notificationService.sendNotificaitoin(user);
        } catch (MailException e) {
            logger.error("Exception occur while send mail :");
            return new ResponseEntity("Mail not send", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            logger.error("Exception occur while send mail :");
            return new ResponseEntity("Mail not send", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  new ResponseEntity("Mail send successfully", HttpStatus.OK);
    }
}
