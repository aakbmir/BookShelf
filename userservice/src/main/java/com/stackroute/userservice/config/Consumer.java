package com.stackroute.userservice.config;


import com.stackroute.userservice.domain.User;
import com.stackroute.userservice.service.UserServiceImpl;
import com.stackroute.rabbitmq.domain.UserDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer
{
  @Autowired
  private UserServiceImpl userService;

  @RabbitListener(queues = "user_queue")
  public void getUserDtoFromRabbitMq(UserDTO userDTO)
  {
    User user=new User();
    user.setUsername(userDTO.getUsername());
    user.setPassword(userDTO.getPassword());
    userService.saveUser(user);
  }
}
