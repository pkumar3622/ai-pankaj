package com.graphql.service;

import com.graphql.dto.CreateBookInput;
import com.graphql.dto.UpdateBookInput;
import com.graphql.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BookService Tests")
class BookServiceTest {

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
    }

    @Test
    @DisplayName("Should retrieve all books")
    void testGetAllBooks() {
        // When
        List<Book> books = bookService.getAllBooks();

        // Then
        assertNotNull(books);
        assertEquals(3, books.size());
    }

    @Test
    @DisplayName("Should get book by valid ID")
    void testGetBookById_ValidId() {
        // When
        Optional<Book> book = bookService.getBookById("1");

        // Then
        assertTrue(book.isPresent());
        assertEquals("The Great Gatsby", book.get().getTitle());
        assertEquals("F. Scott Fitzgerald", book.get().getAuthor());
    }

    @Test
    @DisplayName("Should return empty when book ID is not found")
    void testGetBookById_InvalidId() {
        // When
        Optional<Book> book = bookService.getBookById("invalid-id");

        // Then
        assertFalse(book.isPresent());
    }

    @Test
    @DisplayName("Should retrieve books by author name")
    void testGetBooksByAuthor() {
        // When
        List<Book> books = bookService.getBooksByAuthor("George Orwell");

        // Then
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("1984", books.get(0).getTitle());
    }

    @Test
    @DisplayName("Should return empty list when author has no books")
    void testGetBooksByAuthor_NoBooks() {
        // When
        List<Book> books = bookService.getBooksByAuthor("Unknown Author");

        // Then
        assertNotNull(books);
        assertEquals(0, books.size());
    }

    @Test
    @DisplayName("Should create a new book successfully")
    void testCreateBook() {
        // Given
        CreateBookInput input = new CreateBookInput(
                "The Hobbit",
                "J.R.R. Tolkien",
                "978-0547928197",
                9.99,
                310,
                "A fantasy adventure novel"
        );
        int initialSize = bookService.getAllBooks().size();

        // When
        Book createdBook = bookService.createBook(input);

        // Then
        assertNotNull(createdBook);
        assertNotNull(createdBook.getId());
        assertEquals("The Hobbit", createdBook.getTitle());
        assertEquals("J.R.R. Tolkien", createdBook.getAuthor());
        assertEquals(9.99, createdBook.getPrice());
        assertEquals(310, createdBook.getPages());
        assertEquals(initialSize + 1, bookService.getAllBooks().size());
    }

    @Test
    @DisplayName("Should update an existing book")
    void testUpdateBook() {
        // Given
        UpdateBookInput input = new UpdateBookInput(
                "1",
                "The Great Gatsby - Revised Edition",
                null,
                12.99,
                "Updated classic novel"
        );

        // When
        Book updatedBook = bookService.updateBook(input);

        // Then
        assertNotNull(updatedBook);
        assertEquals("The Great Gatsby - Revised Edition", updatedBook.getTitle());
        assertEquals(12.99, updatedBook.getPrice());
        assertEquals("Updated classic novel", updatedBook.getDescription());
        // Author should remain unchanged
        assertEquals("F. Scott Fitzgerald", updatedBook.getAuthor());
    }

    @Test
    @DisplayName("Should return null when updating non-existent book")
    void testUpdateBook_NotFound() {
        // Given
        UpdateBookInput input = new UpdateBookInput(
                "non-existent-id",
                "New Title",
                null,
                15.99,
                "Description"
        );

        // When
        Book updatedBook = bookService.updateBook(input);

        // Then
        assertNull(updatedBook);
    }

    @Test
    @DisplayName("Should delete an existing book")
    void testDeleteBook() {
        // Given
        int initialSize = bookService.getAllBooks().size();
        String bookId = "1";

        // When
        boolean deleted = bookService.deleteBook(bookId);

        // Then
        assertTrue(deleted);
        assertEquals(initialSize - 1, bookService.getAllBooks().size());
        assertFalse(bookService.getBookById(bookId).isPresent());
    }

    @Test
    @DisplayName("Should return false when deleting non-existent book")
    void testDeleteBook_NotFound() {
        // Given
        int initialSize = bookService.getAllBooks().size();

        // When
        boolean deleted = bookService.deleteBook("non-existent-id");

        // Then
        assertFalse(deleted);
        assertEquals(initialSize, bookService.getAllBooks().size());
    }

    @Test
    @DisplayName("Should handle multiple creates and deletes correctly")
    void testMultipleOperations() {
        // Given
        CreateBookInput input1 = new CreateBookInput("Book 1", "Author 1", "ISBN1", 10.0, 100, "Desc1");
        CreateBookInput input2 = new CreateBookInput("Book 2", "Author 2", "ISBN2", 15.0, 200, "Desc2");

        // When
        Book book1 = bookService.createBook(input1);
        Book book2 = bookService.createBook(input2);
        int sizeAfterCreates = bookService.getAllBooks().size();

        // Then
        assertNotNull(book1.getId());
        assertNotNull(book2.getId());
        assertNotEquals(book1.getId(), book2.getId());
        assertEquals(5, sizeAfterCreates); // 3 initial + 2 new

        // When - Delete
        boolean deleted = bookService.deleteBook(book1.getId());

        // Then
        assertTrue(deleted);
        assertEquals(4, bookService.getAllBooks().size());
    }
}
