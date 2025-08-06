package com.example.LibraryManagementSystem.utils;

import com.example.LibraryManagementSystem.dto.BookResponse;
import com.example.LibraryManagementSystem.entity.Book;

public class BookUtils {
    public static BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publishedYear(book.getPublishedYear())
                .available(book.isAvailable())
                .build();
    }
}
