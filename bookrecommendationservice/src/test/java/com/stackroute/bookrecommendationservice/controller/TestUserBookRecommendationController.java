package com.stackroute.bookrecommendationservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.stackroute.bookrecommendationservice.domain.Author;
import com.stackroute.bookrecommendationservice.domain.Book;
import com.stackroute.bookrecommendationservice.service.UserRecommendationBookService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRecommendationBookController.class)
public class TestUserBookRecommendationController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRecommendationBookService userRecommendationBookService;

    private Book book;

    private List<Book> bookList;

    @Before
    public void setUp() throws Exception {

        bookList = new ArrayList<>();

        book = new Book();
        Author author = new Author(1,"Larry");
        book = new Book("sci1","Java","updated version",author,3);
        bookList.add(book);

        Author authorObj = new Author(2,"Page");
        book = new Book("txt1","Spring","Boot",authorObj,4);
        bookList.add(book);
    }

    @After
    public void tearDown() throws Exception {
        book = null;

    }

    @Test
    public void testgetAllUserBooksFromRecommendedList() throws Exception{

        when(userRecommendationBookService.getAllBooksFromRecommendedList()).thenReturn(bookList);

        mockMvc.perform(get("/api/v1/userrecommendationbookservice/books")
                .contentType(MediaType.APPLICATION_JSON).content(jsonToString(book)))
                .andExpect(status().isOk()).
                andDo(print());
        verify(userRecommendationBookService,times(1)).getAllBooksFromRecommendedList();


    }

    private static String jsonToString(final Object ob) throws JsonProcessingException {
        String result;

        try{
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            result =jsonContent;

        }catch (JsonProcessingException e){
            result = "Json Processing Error";
        }


        return result;
    }
}





