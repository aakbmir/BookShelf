package com.stackroute.userservice.service;

import com.stackroute.userservice.domain.User;

import java.util.Map;

public interface SecurityTokenGenerator
{
    Map<String,String> generateToken(User user);
}
