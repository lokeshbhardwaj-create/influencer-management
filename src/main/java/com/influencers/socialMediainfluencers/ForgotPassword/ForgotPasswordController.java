package com.influencers.socialMediainfluencers.ForgotPassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgotPasswordController {

    @Autowired
    ForgotPasswordService forgotPasswordService;

    @RequestMapping("/forgotPassword")
    public String getOTPForForgotPassword(@RequestParam String email, @RequestParam String username){
       System.out.println("forgot email password "+email+ "forgot password username"+username);
      return  forgotPasswordService.sendOTP(email,username);
      //  return ResponseEntity.ok("Otp for forgotPassword");
    }
    @RequestMapping("/otpVerify")
    public String OTPVerify(@RequestParam String email, @RequestParam String username,@RequestParam String otp){
        return forgotPasswordService.OTPVerifyForForgotPassword(email,username,otp);
    }
    @RequestMapping("/updatePassword")
    public String updatePassword(@RequestParam String email,@RequestParam String username,@RequestParam String newPassword){
       return forgotPasswordService.updatePassword(email,username,newPassword);
      //  return ResponseEntity.ok("result");
    }
}
