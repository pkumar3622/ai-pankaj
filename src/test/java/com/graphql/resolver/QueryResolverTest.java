package com.graphql.resolver;

import com.graphql.model.Book;
import com.graphql.model.Author;
import com.graphql.service.BookService;
import com.graphql.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("QueryResolver Tests")
class QueryResolverTest {

    private BookService bookService;
    private AuthorService authorService;
    private QueryResolver queryResolver;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
        authorService = new AuthorService();
        queryResolver = new QueryResolver(bookService, authorService);
    }

    @Test
    @DisplayName("Should return all books")
    void testAllBooks() {
        // When
        List<Book> result = queryResolver.allBooks();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("The Great Gatsby", result.get(0).getTitle());
    }

    @Test
    @DisplayName("Should return book by ID")
    void testBookById() {
        // When
        Book result = queryResolver.bookById("1");

        // Then
        assertNotNull(result);
        assertEquals("The Great Gatsby", result.getTitle());
    }

    @Test
    @DisplayName("Should return null when book ID not found")
    void testBookById_NotFound() {
        // When
        Book result = queryResolver.bookById("invalid");

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should return books by author")
    void testBooksByAuthor() {
        // When
        List<Book> result = queryResolver.booksByAuthor("George Orwell");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1984", result.get(0).getTitle());
    }

    @Test
    @DisplayName("Should return empty list for unknown author")
    void testBooksByAuthor_Empty() {
        // When
        List<Book> result = queryResolver.booksByAuthor("Unknown Author");

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Should return all authors")
    void testAllAuthors() {
        // When
        List<Author> result = queryResolver.allAuthors();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("F. Scott Fitzgerald", result.get(0).getName());
    }

    @Test
    @DisplayName("Should return author by ID")
    void testAuthorById() {
        // When
        Author result = queryResolver.authorById("1");

        // Then
        assertNotNull(result);
        assertEquals("F. Scott Fitzgerald", result.getName());
    }

    @Test
    @DisplayName("Should return null when author ID not found")
    void testAuthorById_NotFound() {
        // When
        Author result = queryResolver.authorById("invalid");

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should return author by name")
    void testAuthorByName() {
        // When
        Author result = queryResolver.authorByName("George Orwell");

        // Then
        assertNotNull(result);
        assertEquals("George Orwell", result.getName());
        assertEquals("UK", result.getCountry());
    }

    @Test
    @DisplayName("Should return null when author name not found")
    void testAuthorByName_NotFound() {
        // When
        Author result = queryResolver.authorByName("Unknown");

        // Then
        assertNull(result);
    }
}

