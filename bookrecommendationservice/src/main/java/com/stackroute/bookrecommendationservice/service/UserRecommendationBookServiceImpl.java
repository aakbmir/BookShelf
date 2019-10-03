package com.stackroute.bookrecommendationservice.service;


import com.stackroute.bookrecommendationservice.domain.Book;
import com.stackroute.bookrecommendationservice.exception.BookNotFoundException;
import com.stackroute.bookrecommendationservice.repository.UserRecommendationBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserRecommendationBookServiceImpl implements UserRecommendationBookService {


    private UserRecommendationBookRepository userRecommendationBookRepository;

    @Autowired
    public UserRecommendationBookServiceImpl(UserRecommendationBookRepository userRecommendationBookRepository) {
        this.userRecommendationBookRepository = userRecommendationBookRepository;

    }

    /**
     * This method fetches the list of books from the database and increases the count of the book that has been added and
     * saves the details back in the recommended user table with updated count for the particular book
     *
     * if the book is not found in the list, it saves the book in the database with count set to 1
     * @param book
     * @return book
     */
    @Override
    public Book saveBookToRecommendedList(Book book)
    {
        List<Book> fetchBookObj = userRecommendationBookRepository.findAll();
        System.out.println("author name in if : " + book.getAuthor().toString());
        if (fetchBookObj != null && !fetchBookObj.isEmpty()) {
            boolean bookfound = false;
            for (Book bookObj : fetchBookObj) {
                if (bookObj.getBookId().equals(book.getBookId())) {
                    bookfound = true;
                    int count = bookObj.getCount();
                    bookObj.setCount(++count);
                    userRecommendationBookRepository.save(bookObj);
                }
                if (!bookfound) {
                    book.setCount(1);
                    userRecommendationBookRepository.save(book);
                }
            }
        } else {
            book.setCount(1);
            System.out.println("author name : " + book.getAuthor().toString());
            userRecommendationBookRepository.save(book);
        }
       return book;
    }

    /**
     * This method fetches the list of books from the database returns back only those books where count is greater than 2
     * @return List<Book>
     */
    @Override
    public List<Book> getAllBooksFromRecommendedList() throws BookNotFoundException
    {
        List<Book> book = userRecommendationBookRepository.findAll();
        List<Book> returnBookList=new ArrayList<>();
        if(book != null && !book.isEmpty())
        {
            for(Book bookObj:book)
            {
                if(bookObj.getCount() > 2)
                {
                    returnBookList.add(bookObj);
                }
            }
        }
        return returnBookList;
    }
}