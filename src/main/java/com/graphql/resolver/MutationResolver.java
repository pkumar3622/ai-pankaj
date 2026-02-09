package com.graphql.resolver;

import com.graphql.model.Book;
import com.graphql.dto.CreateBookInput;
import com.graphql.dto.UpdateBookInput;
import com.graphql.service.BookService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MutationResolver {
    private final BookService bookService;

    public MutationResolver(BookService bookService) {
        this.bookService = bookService;
    }

    @MutationMapping
    public Book createBook(@Argument CreateBookInput input) {
        return bookService.createBook(input);
    }

    @MutationMapping
    public Book updateBook(@Argument UpdateBookInput input) {
        return bookService.updateBook(input);
    }

    @MutationMapping
    public Boolean deleteBook(@Argument String id) {
        return bookService.deleteBook(id);
    }
}
