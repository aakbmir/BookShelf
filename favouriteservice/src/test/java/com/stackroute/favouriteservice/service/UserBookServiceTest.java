package com.stackroute.favouriteservice.service;

import com.stackroute.favouriteservice.config.Producer;
import com.stackroute.favouriteservice.domain.Author;
import com.stackroute.favouriteservice.domain.Book;
import com.stackroute.favouriteservice.domain.User;
import com.stackroute.favouriteservice.exception.BookAlreadyExistsException;
import com.stackroute.favouriteservice.exception.BookNotFoundException;
import com.stackroute.favouriteservice.repository.UserBookRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserBookServiceTest {
    @Mock
    private UserBookRepository userBookRepository;
    @Mock
    private Producer producer;
    private User user;
    private Book book;
    private Author author;
    private List<Book> list;

    @InjectMocks
    UserBookServiceImpl userBookService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Author author = new Author(101, "Kevin Jenkins");
        book = new Book("tex23", "The war of the jewels","Fictional",author);
        list = new ArrayList<>();
        list.add(book);
        user = new User("Matt", "mathew@gmail.com", "", list);
    }

    public void tearDown() {
        userBookRepository.deleteAll();
        list = null;
        book = null;
        author = null;
        user = null;
    }

    @Test
    public void testSaveUserBookSuccess() throws BookAlreadyExistsException {
        user = new User("Matt", "Mattew@gmail.com", "", null);
        when(userBookRepository.findByUsername(user.getUsername())).thenReturn(user);
        User fetchUser = userBookService.saveUserBookToFavourites(book, user.getUsername());
        Assert.assertEquals(fetchUser, user);
        verify(userBookRepository, timeout(1)).findByUsername(user.getUsername());
        verify(userBookRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUserBookFromWishList() throws BookNotFoundException {
        when(userBookRepository.findByUsername(user.getUsername())).thenReturn(user);
        User fetchUser = userBookService.deleteUserBookFromFavourites(user.getUsername(), book.getBookId());
        Assert.assertEquals(fetchUser, user);
        verify(userBookRepository, timeout(1)).findByUsername(user.getUsername());
        verify(userBookRepository, times(1)).save(user);
    }

    @Test
    public void testGetAllUserBookFromWishList() throws Exception {
        when(userBookRepository.findByUsername(user.getUsername())).thenReturn(user);
        List<Book> fetchedList = userBookService.getAllUserBookFromFavourites(user.getUsername());
        Assert.assertEquals(fetchedList, list);
        verify(userBookRepository, times(1)).findByUsername(user.getUsername());
    }
}