package com.stackroute.favouriteservice.controller;

import com.stackroute.favouriteservice.domain.Book;
import com.stackroute.favouriteservice.domain.User;
import com.stackroute.favouriteservice.exception.BookAlreadyExistsException;
import com.stackroute.favouriteservice.exception.BookNotFoundException;
import com.stackroute.favouriteservice.exception.InvalidFormException;
import com.stackroute.favouriteservice.exception.UserAlreadyExistsException;
import com.stackroute.favouriteservice.service.UserBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/userbookservice")
public class UserBookController
{
    private UserBookService userBookService;
    private ResponseEntity responseEntity;

    public UserBookController()
    {
    }

    @Autowired
    public UserBookController(UserBookService userBookService)
    {
        this.userBookService = userBookService;
    }


    /**
     *
     * @param user
     * @return
     * @throws UserAlreadyExistsException
     */
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody User user) throws UserAlreadyExistsException,InvalidFormException
    {
        try
        {
            if(user.getUsername() != null && user.getEmail() != null && user.getPassword() != null) {
                userBookService.registerUser(user);
                responseEntity = new ResponseEntity<User>(user, HttpStatus.CREATED);
            }
            else
            {
                throw new InvalidFormException();
            }
        }
        catch (UserAlreadyExistsException e)
        {
            throw new UserAlreadyExistsException();
        }
        return responseEntity;
    }


    /**
     *
     * @param book
     * @param username
     * @return
     * @throws BookAlreadyExistsException
     */
    @PostMapping("/user/{username}/book")
    public ResponseEntity<?> saveUserBookToFavourites(@RequestBody Book book, @PathVariable("username") String username) throws BookAlreadyExistsException
    {
        try
        {
            User user = userBookService.saveUserBookToFavourites(book, username);
            responseEntity = new ResponseEntity<User>(user, HttpStatus.CREATED);
        }
        catch (BookAlreadyExistsException e)
        {
            throw new BookAlreadyExistsException();
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     *
     * @param username
     * @param bookId
     * @return
     * @throws BookNotFoundException
     */
    @DeleteMapping("user/{username}/{bookId}")
    public ResponseEntity<?> deleteUserBookFromWishList(@PathVariable("username") String username, @PathVariable("bookId") String bookId) throws BookNotFoundException
    {
        ResponseEntity responseEntity = null;
        try
        {
            User user = userBookService.deleteUserBookFromFavourites(username, bookId);
            responseEntity = new ResponseEntity(user, HttpStatus.OK);
        }
        catch (BookNotFoundException e)
        {
            throw new BookNotFoundException();
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     *
     * @param username
     * @return
     */
    @GetMapping("user/{username}/books")
    public ResponseEntity<?> getAllUserBookFromWishList(@PathVariable("username") String username)
    {
        try
        {
            responseEntity = new ResponseEntity(userBookService.getAllUserBookFromFavourites(username), HttpStatus.OK);
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     *
     * @param book
     * @param username
     * @return
     * @throws BookNotFoundException
     */
    @PatchMapping("user/{username}/book")
    public ResponseEntity<?> updateCommentsForUserBook(@RequestBody Book book, @PathVariable("username") String username) throws BookNotFoundException
    {
        try
        {
            userBookService.updateCommentForBook(book.getComments(), book.getBookId(), username);
            responseEntity = new ResponseEntity(book, HttpStatus.OK);
        }
        catch (BookNotFoundException e)
        {
            throw new BookNotFoundException();
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}