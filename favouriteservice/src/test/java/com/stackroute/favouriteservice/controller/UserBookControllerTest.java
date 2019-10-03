package com.stackroute.favouriteservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.favouriteservice.domain.Author;
import com.stackroute.favouriteservice.domain.Book;
import com.stackroute.favouriteservice.domain.User;
import com.stackroute.favouriteservice.exception.BookAlreadyExistsException;
import com.stackroute.favouriteservice.service.UserBookService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserBookController.class)
public class UserBookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserBookService userBookService;


    private Author author;

    private Book book;
    private User user;
    private List<Book> bookList;


    @Before
    public void setUp() {
        bookList = new ArrayList<>();

        author = new Author(1, "Tolkien");
        book = new Book("Fic1", "The Hobbit","Introductory Book", author);
        bookList.add(book);

        author = new Author(2, "Adrian");
        book = new Book("Sci1", "Realm of the Lord Rings","Beyond the portal",  author);
        bookList.add(book);

        user = new User("prima", "prima@gmail.com", "", bookList);
    }

    @After
    public void tearDown() {

        author = null;
        book = null;
        user = null;
    }

    @Test
    public void testsaveBookSuccess() throws Exception {
        when(userBookService.saveUserBookToFavourites(any(), eq(user.getUsername()))).thenReturn(user);

        mockMvc.perform(post("/api/v1/userbookservice/user/{username}/book", user.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(book)))
                .andExpect(status().isCreated())
                .andDo(print());
        verify(userBookService, times(1)).saveUserBookToFavourites(any(), eq(user.getUsername()));
    }

    @Test
    public void testSaveBookFailure() throws Exception {
        when(userBookService.saveUserBookToFavourites(any(), eq(user.getUsername()))).thenThrow(BookAlreadyExistsException.class);

        mockMvc.perform(post("/api/v1/userbookservice/user/{username}/book", user.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(book)))
                .andExpect(status().isConflict())
                .andDo(print());
        verify(userBookService, times(1)).saveUserBookToFavourites(any(), eq(user.getUsername()));
    }

    @Test
    public void testDeleteBook() throws Exception {
        when(userBookService.deleteUserBookFromFavourites(user.getUsername(), book.getBookId())).thenReturn(user);

        mockMvc.perform(delete("/api/v1/userbookservice/user/{username}/{bookId}", user.getUsername(), book.getBookId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(book)))
                .andExpect(status().isOk())
                .andDo(print());
        verify(userBookService, times(1)).deleteUserBookFromFavourites(user.getUsername(), book.getBookId());

    }

    @Test
    public void getAllBookFromWishList() throws Exception {
        when(userBookService.getAllUserBookFromFavourites(user.getUsername())).thenReturn(bookList);

        mockMvc.perform(get("/api/v1/userbookservice/user/{username}/books", user.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(book)))
                .andExpect(status().isOk())
                .andDo(print());
        verify(userBookService, times(1)).getAllUserBookFromFavourites(user.getUsername());
    }

    private static String jsonToString(final Object obj) {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            result = "Json Processing error";
        }
        return result;
    }
}
