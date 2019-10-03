package com.stackroute.favouriteservice.service;

import com.stackroute.favouriteservice.domain.Book;
import com.stackroute.favouriteservice.domain.User;
import com.stackroute.favouriteservice.exception.BookAlreadyExistsException;
import com.stackroute.favouriteservice.exception.BookNotFoundException;
import com.stackroute.favouriteservice.exception.UserAlreadyExistsException;

import java.util.List;

public interface UserBookService
{
    User saveUserBookToFavourites(Book book, String username) throws BookAlreadyExistsException;

    User deleteUserBookFromFavourites(String username, String trackId) throws BookNotFoundException;

    List<Book> getAllUserBookFromFavourites(String username) throws Exception;

    User registerUser(User user) throws UserAlreadyExistsException;

    User updateCommentForBook(String comments, String bookId, String username) throws BookNotFoundException;
}