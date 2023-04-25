package com.example.service;

import com.example.dto.auth.AuthResponseDto;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromAccount;
    @Value("${server.host}")
    private String serverHost;

    public void sendRegistrationEmail(String toAccount) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Registration verification.\n");
        stringBuilder.append("Click to below link to complete registration\n");
        stringBuilder.append("Link: ");
        stringBuilder.append(serverHost).append("/api/v1/auth/email/verification/");
        stringBuilder.append(JwtUtil.encode(toAccount));
        // https://kun.uz/api/v1/auth//email/verification/dasdasdasd.asdasdad.asda
        // localhost:8080/api/v1/auth/email/verification/dasdasdasd.asdasdad.asda
        sendEmail(toAccount, "Registration", stringBuilder.toString());
    }


    void sendEmail(String toAccount, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAccount);
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(text);
        javaMailSender.send(msg);
    }
    public void sendRegistrationEmailMime(String toAccount) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<h1 style=\"text-align: center\">Registration verification</h1>");
        stringBuilder.append("<br><br>");
        // <p><a href="asd.dasdad.asdaasda">Click to the link to complete registration</a></p>
        stringBuilder.append("<p><a href=\"");
        stringBuilder.append(serverHost).append("/api/v1/auth/email/verification/");
        stringBuilder.append(JwtUtil.encode(toAccount)).append("\">");
        stringBuilder.append("Click to the link to complete registration</a></p>");
        //sendEmailMime(toAccount, "Registration", stringBuilder.toString());
    }
}
