package com.finns.follow.exception;

public class FollowNotFoundException extends RuntimeException {
    public FollowNotFoundException(String message) {
        super(message);
    }
}