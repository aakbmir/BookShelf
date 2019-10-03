package com.stackroute.bookrecommendationservice.config;

import com.stackroute.bookrecommendationservice.domain.Author;
import com.stackroute.bookrecommendationservice.domain.Book;
import com.stackroute.bookrecommendationservice.service.UserRecommendationBookServiceImpl;
import com.stackroute.rabbitmq.domain.BookDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer
{
    @Autowired
    private UserRecommendationBookServiceImpl userRecommendedService;

    @RabbitListener(queues = "user_recommended_queue")
    public void getUserRecommendedDtoFromRabbitMq(BookDTO bookDTO)
    {
        System.out.println("bookDTO in consumer : " + bookDTO);
        Book book=new Book();
        book.setBookId(bookDTO.getBookId());
        book.setBookName(bookDTO.getBookName());
        book.setComments(bookDTO.getComments());
        book.setAuthor(new Author());
        book.getAuthor().setAuthorId(bookDTO.getAuthor().getAuthorId());
        book.getAuthor().setAuthorName(bookDTO.getAuthor().getAuthorName());
        userRecommendedService.saveBookToRecommendedList(book);
    }
}
