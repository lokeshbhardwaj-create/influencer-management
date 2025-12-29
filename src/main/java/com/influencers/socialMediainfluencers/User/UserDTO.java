package com.influencers.socialMediainfluencers.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String name;
    private String emailId;
    private String password;
    private String otp;
   private String role;
   private UserType userType;

}
