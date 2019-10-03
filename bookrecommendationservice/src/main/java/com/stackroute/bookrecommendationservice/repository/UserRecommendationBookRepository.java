package com.stackroute.bookrecommendationservice.repository;

import com.stackroute.bookrecommendationservice.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRecommendationBookRepository extends MongoRepository<Book, String>
{
}
