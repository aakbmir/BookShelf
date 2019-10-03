package com.stackroute.userservice.controller;

import com.stackroute.userservice.domain.User;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.service.SecurityTokenGenerator;
import com.stackroute.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/userservice")
public class UserController
{
    private ResponseEntity responseEntity;
    private UserService userService;
    private SecurityTokenGenerator securityTokenGenerator;

    public UserController(UserService userService, SecurityTokenGenerator securityTokenGenerator)
    {
        this.userService = userService;
        this.securityTokenGenerator = securityTokenGenerator;
    }


    /**
     * This method generates the TokenAPI for successfully registered user else throws an error if the selected user is not registered
     * @param user
     * @return
     * @throws UserNotFoundException
     */
    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody User user) throws UserNotFoundException
    {
        Map<String, String> map = null;
        try
        {
            User userObj = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
            if (userObj.getUsername().equals(user.getUsername()))
            {
                map = securityTokenGenerator.generateToken(user);
            }
            responseEntity = new ResponseEntity(map, HttpStatus.OK);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity("Try after sometime!!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
