package com.influencers.socialMediainfluencers.Admin;

import com.influencers.socialMediainfluencers.User.User;
import com.influencers.socialMediainfluencers.User.UserRepository;
import com.influencers.socialMediainfluencers.User.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;


    public String getAllUsers(){
        userRepository.findAll();
        return "all User Fetched Successfully";
    }

    public String getSpecificUser(Long userId){
        userRepository.findById(userId);
        return userId  +  "user has been fetched";
    }

    public String updateUserRoleAndType(String username, String role, UserType userType) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "User not found";
        }
        user.setRole(role);
        user.setUserType(userType);
        userRepository.save(user);
        return "User role updated to " + role + " and user type updated to " + userType;
    }


}
