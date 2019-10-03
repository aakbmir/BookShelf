package com.stackroute.userservice.service;

import com.stackroute.userservice.domain.User;
import com.stackroute.userservice.exception.UserNotFoundException;

public interface UserService
{
    public User saveUser(User user);
    public User findByUsernameAndPassword(String username,String password) throws UserNotFoundException;
}
