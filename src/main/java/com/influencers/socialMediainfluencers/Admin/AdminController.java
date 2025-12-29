package com.influencers.socialMediainfluencers.Admin;

import com.influencers.socialMediainfluencers.User.UserRepository;
import com.influencers.socialMediainfluencers.User.UserService;
import com.influencers.socialMediainfluencers.User.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/getAllUsers")
    public String getAllUsers(){
       return adminService.getAllUsers();
    }
    @GetMapping("/getSingleUser")
    public String getSpecificUser(Long userId){
        return adminService.getSpecificUser(userId);
    }

    @PostMapping("/updateRole")
    public ResponseEntity<String> updateUserRole(@RequestParam String username,@RequestParam String role,@RequestParam UserType userType) {
        return ResponseEntity.ok(adminService.updateUserRoleAndType(username,role,userType));
    }


}
