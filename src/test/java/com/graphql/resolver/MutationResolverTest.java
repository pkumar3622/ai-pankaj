package com.graphql.resolver;

import com.graphql.dto.CreateBookInput;
import com.graphql.dto.UpdateBookInput;
import com.graphql.model.Book;
import com.graphql.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MutationResolver Tests")
class MutationResolverTest {

    private BookService bookService;
    private MutationResolver mutationResolver;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
        mutationResolver = new MutationResolver(bookService);
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
        Book result = mutationResolver.createBook(input);

        // Then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("The Hobbit", result.getTitle());
        assertEquals("J.R.R. Tolkien", result.getAuthor());
        assertEquals(9.99, result.getPrice());
        assertEquals(310, result.getPages());
        assertEquals(initialSize + 1, bookService.getAllBooks().size());
    }

    @Test
    @DisplayName("Should update an existing book")
    void testUpdateBook() {
        // Given
        UpdateBookInput input = new UpdateBookInput(
                "1",
                "The Great Gatsby - Revised",
                null,
                12.99,
                "Updated description"
        );

        // When
        Book result = mutationResolver.updateBook(input);

        // Then
        assertNotNull(result);
        assertEquals("The Great Gatsby - Revised", result.getTitle());
        assertEquals(12.99, result.getPrice());
        assertEquals("Updated description", result.getDescription());
    }

    @Test
    @DisplayName("Should return null when updating non-existent book")
    void testUpdateBook_NotFound() {
        // Given
        UpdateBookInput input = new UpdateBookInput(
                "invalid",
                "New Title",
                null,
                15.99,
                "Description"
        );

        // When
        Book result = mutationResolver.updateBook(input);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should delete an existing book")
    void testDeleteBook() {
        // Given
        int initialSize = bookService.getAllBooks().size();

        // When
        Boolean result = mutationResolver.deleteBook("1");

        // Then
        assertTrue(result);
        assertEquals(initialSize - 1, bookService.getAllBooks().size());
        assertNull(mutationResolver.deleteBook("1") ? bookService.getBookById("1").orElse(null) : bookService.getBookById("1").orElse(null));
    }

    @Test
    @DisplayName("Should return false when deleting non-existent book")
    void testDeleteBook_NotFound() {
        // Given
        int initialSize = bookService.getAllBooks().size();

        // When
        Boolean result = mutationResolver.deleteBook("invalid");

        // Then
        assertFalse(result);
        assertEquals(initialSize, bookService.getAllBooks().size());
    }

    @Test
    @DisplayName("Should handle multiple mutations sequentially")
    void testMultipleMutations() {
        // Given
        CreateBookInput createInput = new CreateBookInput(
                "New Book",
                "New Author",
                "ISBN123",
                15.99,
                200,
                "A new book"
        );
        int initialSize = bookService.getAllBooks().size();

        // When - Create
        Book created = mutationResolver.createBook(createInput);
        assertNotNull(created);
        assertTrue(bookService.getAllBooks().size() > initialSize);

        // When - Update
        UpdateBookInput updateInput = new UpdateBookInput(
                created.getId(),
                "Updated Title",
                "Updated Author",
                19.99,
                "Updated Description"
        );
        Book updated = mutationResolver.updateBook(updateInput);

        // Then
        assertNotNull(updated);
        assertEquals("Updated Title", updated.getTitle());
        assertEquals("Updated Author", updated.getAuthor());

        // When - Delete
        Boolean deleted = mutationResolver.deleteBook(created.getId());

        // Then
        assertTrue(deleted);
        assertEquals(initialSize, bookService.getAllBooks().size());
    }

    @Test
    @DisplayName("Should handle partial updates correctly")
    void testPartialUpdate() {
        // Given
        Book originalBook = bookService.getBookById("2").get();
        String originalAuthor = originalBook.getAuthor();

        UpdateBookInput input = new UpdateBookInput(
                "2",
                "New Title",
                null,
                null,
                null
        );

        // When
        Book result = mutationResolver.updateBook(input);

        // Then
        assertNotNull(result);
        assertEquals("New Title", result.getTitle());
        assertEquals(originalAuthor, result.getAuthor()); // Author should remain unchanged
        assertEquals(originalBook.getPrice(), result.getPrice()); // Price should remain unchanged
    }
}

