package com.stackroute.userservice.serviceTest;

import com.stackroute.userservice.domain.User;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.repository.UserRepository;
import com.stackroute.userservice.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.MockitoAnnotations;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserServiceTest
{
    @Mock
    private UserRepository userRepository;
    private User user;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp()
    {
       MockitoAnnotations.initMocks(this);
        user = new User();
        user.setUsername("Ricky");
        user.setPassword("Ricky123");
    }



    @Test
    public void testSaveUserSuccess()
    {
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User fetchedUser = userService.saveUser(user);
        Assert.assertEquals(user,fetchedUser);
        verify(userRepository,times(1)).save(user);

    }

    @Test
    public void testUserLoginSuccess() throws UserNotFoundException
    {
        Mockito.when(userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword())).thenReturn(user);
        User fetchedUser = userService.findByUsernameAndPassword(user.getUsername(),user.getPassword());
        Assert.assertEquals(user.getUsername(),fetchedUser.getUsername());
        verify(userRepository,times(1)).findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}