package com.stackroute.bookrecommendationservice.controller;

import com.stackroute.bookrecommendationservice.domain.Book;
import com.stackroute.bookrecommendationservice.exception.BookAlreadyExistsException;
import com.stackroute.bookrecommendationservice.service.UserRecommendationBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/userrecommendationbookservice")
public class UserRecommendationBookController
{
    private UserRecommendationBookService userRecommendationBookService;
    private ResponseEntity responseEntity;

    public UserRecommendationBookController()
    {
    }

    @Autowired
    public UserRecommendationBookController(final UserRecommendationBookService userRecommendationBookService)
    {
        this.userRecommendationBookService = userRecommendationBookService;
    }

    @CrossOrigin(origins = {"http://localhost:8080","http://localhost:8086","http://localhost:8089"})
    @GetMapping("/books")
    public ResponseEntity getAllBooksFromRecommendedList()
    {
        try
        {
            responseEntity = new ResponseEntity(userRecommendationBookService.getAllBooksFromRecommendedList(),HttpStatus.OK);
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}