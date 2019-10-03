package com.stackroute.rabbitmq.domain;


public class BookDTO {

    private String bookId;

    private String bookName;

    private String comments;

    private AuthorDTO author;

    public BookDTO() {
    }

    public BookDTO(String bookId, String bookName, String comments, AuthorDTO author) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.comments = comments;
        this.author = author;
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

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", comments='" + comments + '\'' +
                ", author=" + author +
                '}';
    }
}