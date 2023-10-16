package com.influemark.app.utils.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

   private final JavaMailSender javaMailSender;

    public void sendEmail(
            String toEmail,
            String subject,
            String body
    ){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("elsonkhoza00@gmail.com");
        javaMailSender.send(message);

    }
}
