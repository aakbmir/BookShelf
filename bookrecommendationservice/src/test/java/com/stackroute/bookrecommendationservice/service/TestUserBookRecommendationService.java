package com.stackroute.bookrecommendationservice.service;


import com.stackroute.bookrecommendationservice.domain.Author;
import com.stackroute.bookrecommendationservice.domain.Book;
import com.stackroute.bookrecommendationservice.repository.UserRecommendationBookRepository;;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class TestUserBookRecommendationService {

    @Mock
    private UserRecommendationBookRepository userRecommendationBookRepository;
    @Mock
    private Book book;

    private List<Book> list;

    @InjectMocks
    private UserRecommendationBookServiceImpl recommendationBookServiceImpl;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        Author author = new Author(1,"Larry");
        book = new Book("sci1","Java","updated version",author,5);

        list = new ArrayList<>();
        list.add(book);

    }

    @After
    public void tearDown(){

        userRecommendationBookRepository.deleteAll();
        list = null;
        book = null;
    }

    @Test
    public void testSaveBook() {

        Book fetchedBook = recommendationBookServiceImpl.saveBookToRecommendedList(book);
        Assert.assertEquals(fetchedBook,book);
        Mockito.verify(userRecommendationBookRepository,Mockito.times(1)).save(book);

    }

    @Test
    public void testGetAllRecommendedBooks() throws Exception {
        recommendationBookServiceImpl.saveBookToRecommendedList(book);
        recommendationBookServiceImpl.saveBookToRecommendedList(book);
        recommendationBookServiceImpl.saveBookToRecommendedList(book);
        recommendationBookServiceImpl.saveBookToRecommendedList(book);
        List<Book> fetchedlist = recommendationBookServiceImpl.getAllBooksFromRecommendedList();
        Assert.assertNotNull(fetchedlist);
        Mockito.verify(userRecommendationBookRepository,Mockito.times(5)).findAll();
    }

}


