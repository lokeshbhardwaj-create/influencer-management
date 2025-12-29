package com.influencers.socialMediainfluencers.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtpVerificationController {
    @Autowired
    UserService userService;

    @PostMapping("/SignUp")
    public String saveUserTemp(@RequestParam String email, @RequestParam String name){
        System.out.println("reaching till here "+ email + "name is also reaching till here "+ name);
       return userService.SaveTempUser(email,name);

    }
}
