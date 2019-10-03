package com.stackroute.rabbitmq.domain;

public class AuthorDTO {

    private int authorId;

    private String authorName;

    public AuthorDTO(int authorId, String authorName) {
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public AuthorDTO() {
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                '}';
    }
}
