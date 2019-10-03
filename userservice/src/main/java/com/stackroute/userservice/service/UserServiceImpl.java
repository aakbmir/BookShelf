package com.stackroute.userservice.service;

import com.stackroute.userservice.domain.User;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    /**
     * This method has only been kept for Testing Purpose as it is not directly been used in from the application
     * @param user
     * @return
     */
    @Override
    public User saveUser(User user)
    {
        return userRepository.save(user);
    }

    /**
     * This user looks for the registered user by based on the username and password and throws an error if the user is not found
     * @param username
     * @param password
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) throws UserNotFoundException
    {
       User user = userRepository.findByUsernameAndPassword(username,password);
       if(user == null)
       {
           throw new UserNotFoundException();
       }
       return user;
    }
}
