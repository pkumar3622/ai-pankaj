package com.graphql.service;

import com.graphql.model.Author;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final List<Author> authors = new ArrayList<>();

    public AuthorService() {
        // Initialize with sample data
        authors.add(new Author("1", "F. Scott Fitzgerald", "fitzgerald@example.com", "USA", 1));
        authors.add(new Author("2", "Harper Lee", "harper@example.com", "USA", 1));
        authors.add(new Author("3", "George Orwell", "orwell@example.com", "UK", 5));
    }

    public List<Author> getAllAuthors() {
        return new ArrayList<>(authors);
    }

    public Optional<Author> getAuthorById(String id) {
        return authors.stream()
                .filter(author -> author.getId().equals(id))
                .findFirst();
    }

    public Optional<Author> getAuthorByName(String name) {
        return authors.stream()
                .filter(author -> author.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
