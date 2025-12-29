package com.influencers.socialMediainfluencers.User;
import com.influencers.socialMediainfluencers.Email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    OtpRepository otpRepository;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }


    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
        return String.valueOf(otp);
    }

    public String SaveTempUser(String email, String name){
        if(email == null || name.isEmpty()){
            throw new RuntimeException("Email is required");
        }
          String otp =  generateOtp();
        emailService.sendEmail(email,otp);
        OtpVerification otpVerification = new OtpVerification();
        otpVerification.setName(name);
        otpVerification.setOtp(otp);
        otpVerification.setEmail(email);
        otpVerification.setCreatedAt(LocalDateTime.now());
        otpRepository.save(otpVerification);
      return "otp has been send";
    }
    public String verifyOtp(String name, String email, String otp) {
        OtpVerification latestOtp = otpRepository.findTopByEmailAndNameOrderByCreatedAtDesc(email, name);
        System.out.println("Latest OTP fetched: " + latestOtp);

        if (latestOtp != null) {
            Duration duration = Duration.between(latestOtp.getCreatedAt(), LocalDateTime.now());
            if (duration.toMinutes() > 1) {
                return "Otp is expired";
            }
            if (latestOtp.getOtp().equals(otp)) {
                // Mark the OTP as verified
                latestOtp.setVerified(true);
                otpRepository.save(latestOtp);
                return "Otp verify successfully";
            }
        }
        return "Otp doesn't match or expired";
    }


    public User saveUser(UserDTO userDTO) {

        if (userRepository.existsByEmailId(userDTO.getEmailId())) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        String username = userDTO.getName().toLowerCase() + randomNumber;

        User user1 = new User();
        user1.setName(userDTO.getName());
        user1.setEmailId(userDTO.getEmailId());
        user1.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user1.setUsername(username);

      user1.setRole("User");
      user1.setUserType(UserType.GENERAL);
        return userRepository.save(user1);
    }

    public User userLogin(String email, String password) throws Exception {
        System.out.println("Received bohot badia email: " + email);
        System.out.println("Received  hard password: " + password );

        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new Exception("User not found"));

        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            throw new Exception("Invalid credentials");
        }
    }

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmailId(user.getEmailId());
        // Add other fields as needed
        return userDTO;
    }
}
