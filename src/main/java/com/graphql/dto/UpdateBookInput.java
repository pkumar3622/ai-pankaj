package com.graphql.dto;

public class UpdateBookInput {
    private String id;
    private String title;
    private String author;
    private Double price;
    private String description;

    public UpdateBookInput() {
    }

    public UpdateBookInput(String id, String title, String author, Double price, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

