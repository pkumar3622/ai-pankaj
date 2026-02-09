package com.graphql.model;

public class Author {
    private String id;
    private String name;
    private String email;
    private String country;
    private Integer booksPublished;

    public Author() {
    }

    public Author(String id, String name, String email, String country, Integer booksPublished) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.country = country;
        this.booksPublished = booksPublished;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getBooksPublished() {
        return booksPublished;
    }

    public void setBooksPublished(Integer booksPublished) {
        this.booksPublished = booksPublished;
    }
}

