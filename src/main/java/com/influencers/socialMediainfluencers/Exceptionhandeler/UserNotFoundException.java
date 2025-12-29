package com.influencers.socialMediainfluencers.Exceptionhandeler;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
