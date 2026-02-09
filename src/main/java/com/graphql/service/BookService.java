package com.graphql.service;

import com.graphql.model.Book;
import com.graphql.dto.CreateBookInput;
import com.graphql.dto.UpdateBookInput;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();

    public BookService() {
        // Initialize with sample data
        books.add(new Book("1", "The Great Gatsby", "F. Scott Fitzgerald", "978-0743273565", 10.99, 180, "A classic American novel"));
        books.add(new Book("2", "To Kill a Mockingbird", "Harper Lee", "978-0061120084", 12.99, 281, "A gripping tale of racial injustice"));
        books.add(new Book("3", "1984", "George Orwell", "978-0451524935", 11.99, 328, "A dystopian novel"));
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public Optional<Book> getBookById(String id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    public List<Book> getBooksByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .toList();
    }

    public Book createBook(CreateBookInput input) {
        Book book = new Book();
        book.setId(UUID.randomUUID().toString());
        book.setTitle(input.getTitle());
        book.setAuthor(input.getAuthor());
        book.setIsbn(input.getIsbn());
        book.setPrice(input.getPrice());
        book.setPages(input.getPages());
        book.setDescription(input.getDescription());

        books.add(book);
        return book;
    }

    public Book updateBook(UpdateBookInput input) {
        return books.stream()
                .filter(book -> book.getId().equals(input.getId()))
                .findFirst()
                .map(book -> {
                    if (input.getTitle() != null) {
                        book.setTitle(input.getTitle());
                    }
                    if (input.getAuthor() != null) {
                        book.setAuthor(input.getAuthor());
                    }
                    if (input.getPrice() != null) {
                        book.setPrice(input.getPrice());
                    }
                    if (input.getDescription() != null) {
                        book.setDescription(input.getDescription());
                    }
                    return book;
                })
                .orElse(null);
    }

    public boolean deleteBook(String id) {
        return books.removeIf(book -> book.getId().equals(id));
    }
}
