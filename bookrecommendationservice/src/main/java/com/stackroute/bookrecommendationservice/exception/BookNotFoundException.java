package com.stackroute.bookrecommendationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Book does not exist !!")
public class BookNotFoundException extends Exception {
}
