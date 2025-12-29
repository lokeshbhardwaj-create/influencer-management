package com.influencers.socialMediainfluencers.ForgotPassword;

import com.influencers.socialMediainfluencers.Email.EmailService;
import com.influencers.socialMediainfluencers.User.OtpRepository;
import com.influencers.socialMediainfluencers.User.OtpVerification;
import com.influencers.socialMediainfluencers.User.User;
import com.influencers.socialMediainfluencers.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
@Service
public class ForgotPasswordService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    OtpRepository otpRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
        return String.valueOf(otp);
    }

     public String sendOTP(String email, String username) {
         System.out.println("Finding user with email: " + email + " and username: " + username);

         if (email == null || username.isEmpty()) {
            throw new RuntimeException("email and username is required");
        }
         User user = userRepository.findByEmailIdAndUsername(email.trim(), username.trim());


         System.out.println("User fetched from database: " + user);
        if (user != null) {
            String otp = generateOtp();
            System.out.println("otp is coming here "+otp+ "forgot password username"+username + email);

            emailService.sendEmail(email, otp);
            OtpVerification otpVerification = new OtpVerification();
            otpVerification.setOtp(otp);
            otpVerification.setUsername(username);
            otpVerification.setEmail(email);
            otpVerification.setCreatedAt(LocalDateTime.now());
            emailService.sendEmail(email, otp);

            otpRepository.save(otpVerification);

            return "email sent to" + email;
        } else {
            return "User not found";
        }
    }

    public String OTPVerifyForForgotPassword(String email, String username, String otp) {

        System.out.println("mein to hun paagal "+email+"koi to jeeney de "+username+otp);

        OtpVerification otpVerification = otpRepository.findLatestByEmailAndUsername(email.trim().toLowerCase(), username.trim().toLowerCase());


        System.out.println("mujhe to ye bas dekhna hai "+otpVerification);

        if (otpVerification != null) {
            Duration duration = Duration.between(otpVerification.getCreatedAt(), LocalDateTime.now());
            if (duration.toMinutes() > 1) {
                return "Otp is expired";
            }
            if (otpVerification.getOtp().equals(otp)) {
                // Mark the OTP as verified
                otpVerification.setVerified(true);
                otpRepository.save(otpVerification);
                return "Otp verify successfully";
            }
        }
        return "Otp doesn't match or expired";
    }

    public String updatePassword(String email, String username, String newPassword) {
        OtpVerification otpVerification = otpRepository.findLatestByEmailAndUsername(email.trim().toLowerCase(), username.trim().toLowerCase());

        if (otpVerification != null && otpVerification.isVerified()) {
            User user = userRepository.findByEmailIdAndUsername(email, username);
            user.setPassword(passwordEncoder.encode(newPassword));// Consider encrypting the password before saving
            userRepository.save(user);
            return "Password updated successfully";
        } else {
            return "OTP not verified or expired. Cannot update password.";
        }
    }
}
