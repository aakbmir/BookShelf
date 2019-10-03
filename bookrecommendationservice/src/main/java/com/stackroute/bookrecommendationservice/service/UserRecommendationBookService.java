package com.stackroute.bookrecommendationservice.service;

import com.stackroute.bookrecommendationservice.domain.Book;
import com.stackroute.bookrecommendationservice.exception.BookAlreadyExistsException;

import java.util.List;

public interface UserRecommendationBookService
{
    Book saveBookToRecommendedList(Book book) throws BookAlreadyExistsException;
    List<Book> getAllBooksFromRecommendedList() throws Exception;
}