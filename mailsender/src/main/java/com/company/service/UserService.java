package com.company.service;

import com.company.Entity.*;
import com.company.dto.*;
import net.bytebuddy.utility.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;

    public void register(User user, String url) throws MessagingException, UnsupportedEncodingException {
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        userRepository.save(user);
        sendVerificationEmail(user, url);
    }

    public void sendVerificationEmail(User user, String url) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "your email address";
        String senderName="Rinat";
        String subject = "Please confirm your email address";
        String content = "Dear [[name]], <br>"
                + "Please click the link below to verify your registration: <br>"
                + "<h4><a href=\"[[url]]\" target=\"_blank\">click here to verify your email</a></h4>"
                + "Thank you! <br>"
                +  "Best regards, "+ senderName;

        content = content.replace("[[name]]", user.getFullName());
        String verifyUrl = url + "/verify?code=" + user.getVerificationCode();
        content = content.replace("[[url]]", verifyUrl);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);

        emailSender.send(message);
    }

    public boolean verify(String verificationCode){
        Optional<User> user = userRepository.findByVerificationCode(verificationCode);

        if(user.isEmpty() || user.get().isEnabled()){
            return false;
        } else{
            user.get().setVerificationCode(null);
            user.get().setEnabled(true);
            User u = user.get();
            userRepository.save(u);
            return true;
        }
    }
}
