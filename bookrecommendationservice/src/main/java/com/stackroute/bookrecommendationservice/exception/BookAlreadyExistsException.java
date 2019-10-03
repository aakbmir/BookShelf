package com.stackroute.bookrecommendationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Book already exists in the favourite List !!")
public class BookAlreadyExistsException extends Exception {
}
