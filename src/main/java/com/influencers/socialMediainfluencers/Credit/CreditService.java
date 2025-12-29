package com.influencers.socialMediainfluencers.Credit;
import com.influencers.socialMediainfluencers.Exceptionhandeler.InsufficientCreditsException;
import com.influencers.socialMediainfluencers.Exceptionhandeler.UserNotFoundException;
import com.influencers.socialMediainfluencers.User.User;
import com.influencers.socialMediainfluencers.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditService {

    @Autowired
 private  UserRepository userRepository;

    public void addCredits(Long userId, int credit){

        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("Please SignUp Before Payment"));
        user.setCredit(user.getCredit()+credit);
        userRepository.save(user);
    }

public int getCredits(Long userId){
   User user =  userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("user not found"));
return user.getCredit();
}

public void deductCredits(Long userId, int amount){

    User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("user not found"));
    if (user.getCredit() >= amount){

        user.setCredit(user.getCredit()-amount);
        userRepository.save(user);}
    else{
        throw new InsufficientCreditsException("Not enough credits");
    }
}
}
