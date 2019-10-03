package com.stackroute.rabbitmq.domain;

import java.util.List;

public class UserDTO {
    private String username;
    private String email;
    private String password;
    private List<BookDTO> bookList;

    public UserDTO() {
    }

    public UserDTO(String username, String email, String password, List<BookDTO> bookList) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.bookList = bookList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<BookDTO> getBookList() {
        return bookList;
    }

    public void setBookList(List<BookDTO> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", bookList=" + bookList +
                '}';
    }
}
