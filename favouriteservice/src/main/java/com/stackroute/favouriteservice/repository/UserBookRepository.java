package com.stackroute.favouriteservice.repository;

import com.stackroute.favouriteservice.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserBookRepository extends MongoRepository<User, String>
{
    public User findByUsername(String username);
}
