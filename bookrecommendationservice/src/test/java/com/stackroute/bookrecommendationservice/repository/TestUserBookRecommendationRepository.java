package com.stackroute.bookrecommendationservice.repository;

import com.stackroute.bookrecommendationservice.domain.Author;
import com.stackroute.bookrecommendationservice.domain.Book;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataMongoTest
public class TestUserBookRecommendationRepository {

    @Autowired
    private UserRecommendationBookRepository userRecommendationBookRepository;

    private Book book;

    @Before
    public void setUp(){

        Author author = new Author(1,"Larry");
        book = new Book("sci1","Java","updated version",author,5);
        List list = new ArrayList();
        list.add(book);

    }

    @After
    public void tearDown(){
        userRecommendationBookRepository.deleteAll();
    }

    @Test
    public void testSaveBook(){

        System.out.println("book : " + book);
        Book fetchedBook= userRecommendationBookRepository.save(book);
        // List<Article> list = articleRecommendationRepository.findAll();
        Assert.assertEquals(fetchedBook, book);
    }
}




