package com.influencers.socialMediainfluencers.User;

import com.influencers.socialMediainfluencers.Security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtUtils jwtUtils;
@Autowired
    OtpRepository otpRepository;

    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestParam String name,@RequestParam String email,@RequestParam String otp){
        return userService.verifyOtp(name, email, otp);
    }
/*
    @PostMapping("/saveUser")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
      //  userDTO.setRole("GENERAL_USER");
       // userDTO.setUserType(UserType.GENERAL);

        // Check if the OTP is verified
        OtpVerification latestOtp = otpRepository.findTopByEmailAndNameOrderByCreatedAtDesc(userDTO.getEmailId(), userDTO.getName());
        if (latestOtp == null || !latestOtp.isVerified()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            // Save User
            User savedUser = userService.saveUser(userDTO);
            UserDTO savedUserDTO = userService.convertToDTO(savedUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDTO);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

*/

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signupUser(@RequestBody UserDTO userDTO) {
        try {
            // Verify OTP
            OtpVerification latestOtp = otpRepository.findTopByEmailAndNameOrderByCreatedAtDesc(userDTO.getEmailId(), userDTO.getName());
            if (latestOtp == null || !latestOtp.isVerified()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            User savedUser = userService.saveUser(userDTO);
            // Generate JWT Token
            String token = jwtUtils.generateToken(savedUser.getUsername(), savedUser.getRole());
            // Return token
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {

        System.out.println("Received email: " + loginRequest.getEmail());
        System.out.println("Received password: " + loginRequest.getPassword());

        try {
            User user = userService.userLogin(loginRequest.getEmail(), loginRequest.getPassword());

            String token = jwtUtils.generateToken(user.getUsername(), user.getRole());

            return ResponseEntity.ok(new LoginResponse(token));

        } catch (Exception e) {
            LoginResponse errorResponse = new LoginResponse("Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

    }}
