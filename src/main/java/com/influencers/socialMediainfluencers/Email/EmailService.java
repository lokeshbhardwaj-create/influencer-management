package com.influencers.socialMediainfluencers.Email;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender mailSender;

    public void sendEmail(String toEmail, String otp){
        System.out.println("hello im in sendemail"+ toEmail+"  aur bhai kyaa  "+otp);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Your OTP Code");
            message.setText("Your OTP code is:" + otp);
            // Log the email details for debugging
            System.out.println("Sending email to: " + toEmail);
            System.out.println("Subject: Your OTP Code");
            System.out.println("Body: Your OTP code is: " + otp);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MailSendException("Failed to send email", e);
        }
    }
}
