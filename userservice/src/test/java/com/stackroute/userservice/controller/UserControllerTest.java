package com.stackroute.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.userservice.domain.User;
import com.stackroute.userservice.service.SecurityTokenGenerator;
import com.stackroute.userservice.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UserControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SecurityTokenGenerator securityTokenGenerator;
    private User user;

    @InjectMocks
    private UserController userController;


    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        user = new User();
        user.setUsername("John");
        user.setPassword("John123");
    }

    @Test
    public void testLoginSuccess() throws Exception
    {
        Mockito.when(userService.saveUser(user)).thenReturn(user);
        Mockito.when(userService.findByUsernameAndPassword(user.getUsername(),user.getPassword())).thenReturn(user);
        mockMvc.perform(post("/api/v1/userservice/login")
                .contentType(MediaType.APPLICATION_JSON).content(jsonToString(user)))
                .andExpect(status().isOk()).andDo(print());
        verify(userService,times(1)).findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    private static String jsonToString(final Object obj) throws JsonProcessingException
    {
        String result;
        try
        {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            result = jsonContent;
        }
        catch(JsonProcessingException e)
        {
            result = "Json processing Error!!!";
        }
        return result;

    }

}
