package com.stackroute.favouriteservice.repository;

import com.stackroute.favouriteservice.domain.Author;
import com.stackroute.favouriteservice.domain.Book;
import com.stackroute.favouriteservice.domain.User;
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
public class UserBookRepositoryTest
{

    @Autowired
    private UserBookRepository userBookRepository;
    private Book book;
    private User user;

    @Before
    public void setUp()
    {
        Author author = new Author(101, "Mark Cohen");
        book = new Book("rom2", "The two towers","bibliography",  author);
        List<Book> list = new ArrayList<>();
        list.add(book);
        user = new User("christopher", "christ@gmail.com", "", list);
    }

    @After
    public void tearDown()
    {
        userBookRepository.deleteAll();
    }

    @Test
    public void testsaveUserBook()
    {
        userBookRepository.save(user);
        User fetchuser = userBookRepository.findByUsername(user.getUsername());
        List<Book> list = fetchuser.getBookList();
        Assert.assertEquals(list.get(0).getBookId(), user.getBookList().get(0).getBookId());
    }
}