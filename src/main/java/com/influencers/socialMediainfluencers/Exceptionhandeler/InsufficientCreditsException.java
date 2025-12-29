package com.influencers.socialMediainfluencers.Exceptionhandeler;

public class InsufficientCreditsException extends RuntimeException{

    public InsufficientCreditsException(String message) {
        super(message);
    }
}

