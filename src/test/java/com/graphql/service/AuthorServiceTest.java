package com.graphql.service;

import com.graphql.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AuthorService Tests")
class AuthorServiceTest {

    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        authorService = new AuthorService();
    }

    @Test
    @DisplayName("Should retrieve all authors")
    void testGetAllAuthors() {
        // When
        List<Author> authors = authorService.getAllAuthors();

        // Then
        assertNotNull(authors);
        assertEquals(3, authors.size());
    }

    @Test
    @DisplayName("Should get author by valid ID")
    void testGetAuthorById_ValidId() {
        // When
        Optional<Author> author = authorService.getAuthorById("1");

        // Then
        assertTrue(author.isPresent());
        assertEquals("F. Scott Fitzgerald", author.get().getName());
        assertEquals("fitzgerald@example.com", author.get().getEmail());
    }

    @Test
    @DisplayName("Should return empty when author ID is not found")
    void testGetAuthorById_InvalidId() {
        // When
        Optional<Author> author = authorService.getAuthorById("invalid-id");

        // Then
        assertFalse(author.isPresent());
    }

    @Test
    @DisplayName("Should get author by valid name")
    void testGetAuthorByName_ValidName() {
        // When
        Optional<Author> author = authorService.getAuthorByName("George Orwell");

        // Then
        assertTrue(author.isPresent());
        assertEquals("3", author.get().getId());
        assertEquals("UK", author.get().getCountry());
        assertEquals(5, author.get().getBooksPublished());
    }

    @Test
    @DisplayName("Should ignore case when searching by author name")
    void testGetAuthorByName_CaseInsensitive() {
        // When
        Optional<Author> author = authorService.getAuthorByName("harper lee");

        // Then
        assertTrue(author.isPresent());
        assertEquals("Harper Lee", author.get().getName());
    }

    @Test
    @DisplayName("Should return empty when author name is not found")
    void testGetAuthorByName_InvalidName() {
        // When
        Optional<Author> author = authorService.getAuthorByName("Unknown Author");

        // Then
        assertFalse(author.isPresent());
    }

    @Test
    @DisplayName("Should validate all initial authors are loaded")
    void testInitialAuthorsData() {
        // When
        List<Author> authors = authorService.getAllAuthors();

        // Then
        assertEquals(3, authors.size());

        Author author1 = authors.get(0);
        assertEquals("1", author1.getId());
        assertEquals("F. Scott Fitzgerald", author1.getName());

        Author author2 = authors.get(1);
        assertEquals("2", author2.getId());
        assertEquals("Harper Lee", author2.getName());

        Author author3 = authors.get(2);
        assertEquals("3", author3.getId());
        assertEquals("George Orwell", author3.getName());
    }

    @Test
    @DisplayName("Should retrieve author with complete information")
    void testGetAuthorComplete() {
        // When
        Optional<Author> author = authorService.getAuthorByName("Harper Lee");

        // Then
        assertTrue(author.isPresent());
        Author harperLee = author.get();
        assertEquals("harper@example.com", harperLee.getEmail());
        assertEquals("USA", harperLee.getCountry());
        assertEquals(1, harperLee.getBooksPublished());
    }
}
