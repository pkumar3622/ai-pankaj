package com.graphql.resolver;

import com.graphql.model.Book;
import com.graphql.model.Author;
import com.graphql.service.BookService;
import com.graphql.service.AuthorService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class QueryResolver {
    private final BookService bookService;
    private final AuthorService authorService;

    public QueryResolver(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    // Book Queries
    @QueryMapping
    public List<Book> allBooks() {
        return bookService.getAllBooks();
    }

    @QueryMapping
    public Book bookById(@Argument String id) {
        return bookService.getBookById(id).orElse(null);
    }

    @QueryMapping
    public List<Book> booksByAuthor(@Argument String author) {
        return bookService.getBooksByAuthor(author);
    }

    // Author Queries
    @QueryMapping
    public List<Author> allAuthors() {
        return authorService.getAllAuthors();
    }

    @QueryMapping
    public Author authorById(@Argument String id) {
        return authorService.getAuthorById(id).orElse(null);
    }

    @QueryMapping
    public Author authorByName(@Argument String name) {
        return authorService.getAuthorByName(name).orElse(null);
    }
}
