package com.stackroute.favouriteservice.service;

import com.stackroute.favouriteservice.config.Producer;
import com.stackroute.favouriteservice.domain.Author;
import com.stackroute.favouriteservice.domain.Book;
import com.stackroute.favouriteservice.domain.User;
import com.stackroute.favouriteservice.exception.BookAlreadyExistsException;
import com.stackroute.favouriteservice.exception.BookNotFoundException;
import com.stackroute.favouriteservice.exception.UserAlreadyExistsException;
import com.stackroute.favouriteservice.repository.UserBookRepository;
import com.stackroute.rabbitmq.domain.AuthorDTO;
import com.stackroute.rabbitmq.domain.BookDTO;
import com.stackroute.rabbitmq.domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserBookServiceImpl implements UserBookService
{
    private Producer producer;
    private UserBookRepository userBookRepository;

    /**
     *
     * @param userBookRepository
     * @param producer
     */
    @Autowired
    public UserBookServiceImpl(UserBookRepository userBookRepository, Producer producer)
    {
        this.userBookRepository = userBookRepository;
        this.producer = producer;
    }

    /**
     *
     * @param user
     * @return
     * @throws UserAlreadyExistsException
     */
    @Override
    public User registerUser(User user) throws UserAlreadyExistsException
    {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        User fetchedUserObj = userBookRepository.findByUsername((user.getUsername()));
        if (fetchedUserObj != null)
        {
            throw new UserAlreadyExistsException();
        }
        else
        {
            userBookRepository.save(user);
            producer.sendMessageToRabbitMq(userDTO);
        }
        return user;
    }

    /**
     * This method saves the user book into the Favourites list
     *
     * @param book
     * @param username
     * @return
     * @throws BookAlreadyExistsException
     */
    @Override
    public User saveUserBookToFavourites(Book book, String username) throws BookAlreadyExistsException
    {
        // this will fetch the user object based on the username
        User fetchUserObj = userBookRepository.findByUsername(username);
        List<Book> fetchBooks = fetchUserObj.getBookList();

        //looping on the list of book to check if the selected Book is already present in the list.
        // If it is already present , then we will throw BookAlreadyExists Exception else save the book in the user DB
        if (fetchBooks != null && !fetchBooks.isEmpty())
        {
            for (Book bookObj : fetchBooks)
            {
                if (bookObj.getBookId().equals(book.getBookId()))
                {
                    throw new BookAlreadyExistsException();
                }
            }
            fetchBooks.add(book);
            fetchUserObj.setBookList(fetchBooks);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setEmail(fetchUserObj.getEmail());
            List list = new ArrayList();
            list.add(fetchBooks);
            //This method saves the book object in BookDTO Object
            BookDTO bookDTO = fetchRecommendedBookDTO(book);
            userDTO.setBookList(list);
            userBookRepository.save(fetchUserObj);
            //We are pushing the bookDTO in the RabbitMQ queue and this should be listened by our recommend service
            producer.sendToRabbitMqRecommendedBookObject(bookDTO);
        }
        else
        {
            System.out.println("in save book : " + book);
            fetchBooks = new ArrayList<>();
            fetchBooks.add(book);
            fetchUserObj.setBookList(fetchBooks);
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setEmail(fetchUserObj.getEmail());
            List list = new ArrayList();
            list.add(fetchBooks);
            userDTO.setBookList(list);
            //This method saves the book object in BookDTO Object
            BookDTO bookDTO = fetchRecommendedBookDTO(book);
            userBookRepository.save(fetchUserObj);
            //We are pushing the bookDTO in the RabbitMQ queue and this should be listened by our recommend service
            System.out.println("bookDTO in save : " + bookDTO);
            producer.sendToRabbitMqRecommendedBookObject(bookDTO);
        }
        return fetchUserObj;
    }

    /**
     * This method saves the book object in BookDTO Object
     * @param book
     * @return
     */
    public BookDTO fetchRecommendedBookDTO(Book book)
    {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(book.getBookId());
        bookDTO.setBookName(book.getBookName());
        bookDTO.setComments(book.getComments());
        bookDTO.setAuthor(new AuthorDTO());
        bookDTO.getAuthor().setAuthorId(book.getAuthor().getAuthorId());
        bookDTO.getAuthor().setAuthorName(book.getAuthor().getAuthorName());
        return bookDTO;
    }

    /**
     * This method removes the user Book from the favourite list
     * @param username
     * @param bookId
     * @return
     * @throws BookNotFoundException
     */
    @Override
    public User deleteUserBookFromFavourites(String username, String bookId) throws BookNotFoundException
    {
        User fetchUserObj = userBookRepository.findByUsername(username);
        List<Book> fetchBooks = fetchUserObj.getBookList();
        if (fetchBooks.size() > 0)
        {
            for (int i = 0; i < fetchBooks.size(); i++)
            {
                if (bookId.equals(fetchBooks.get(i).getBookId()))
                {
                    fetchBooks.remove(i);
                    fetchUserObj.setBookList(fetchBooks);
                    userBookRepository.save(fetchUserObj);
                    break;
                }
            }
        }
        else
        {
            throw new BookNotFoundException();
        }
        return fetchUserObj;
    }

    /**
     * This method updates the comments of a book in the favourites list
     * @param comments
     * @param bookId
     * @param username
     * @return
     * @throws BookNotFoundException
     */
    @Override
    public User updateCommentForBook(String comments, String bookId, String username) throws BookNotFoundException
    {
        User fetchUserObj = userBookRepository.findByUsername(username);
        List<Book> fetchBooks = fetchUserObj.getBookList();
        if (fetchBooks != null && !fetchBooks.isEmpty())
        {
            for (Book trackObj : fetchBooks)
            {
                if (trackObj.getBookId().equals(bookId))
                {
                    trackObj.setComments(comments);
                    userBookRepository.save(fetchUserObj);
                    break;
                }
            }
        }
        else
        {
            throw new BookNotFoundException();
        }
        return fetchUserObj;
    }

    /**
     * This method fetches all the books stored in the favourite list by the user
     * @param username
     * @return
     * @throws Exception
     */
    @Override
    public List<Book> getAllUserBookFromFavourites(String username) throws Exception
    {
        User user = userBookRepository.findByUsername(username);
        return user.getBookList();
    }
}