package com.example.LibraryManagementSystem.services;

import com.example.LibraryManagementSystem.dto.BookCreateRequest;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.respository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public String addNewBook(BookCreateRequest bookCreateRequest) throws IllegalArgumentException{
        if (bookRepository.existsByIsbn(bookCreateRequest.getIsbn())) {
            throw new IllegalArgumentException("ISBN already exists");
        }

        Book book = Book.builder()
                .title(bookCreateRequest.getTitle())
                .author(bookCreateRequest.getAuthor())
                .isbn(bookCreateRequest.getIsbn())
                .publishedYear(bookCreateRequest.getPublishedYear())
                .available(bookCreateRequest.getAvailable())
                .build();

        Book savedBook = bookRepository.save(book);
        return savedBook.getId();
    }
}
