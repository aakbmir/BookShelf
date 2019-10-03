package com.stackroute.bookrecommendationservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Book
{
    @Id
    private String bookId;

    @JsonProperty("name")
    private String bookName;

    @JsonProperty("comments")
    private String comments;

    private Author author;

    private int count;

    public Book()
    {

    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Book(String bookId, String bookName, String comments, Author author, int count) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.comments = comments;
        this.author = author;
        this.count = count;
    }
}
