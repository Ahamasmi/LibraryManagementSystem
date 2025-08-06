package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.book.*;
import com.example.LibraryManagementSystem.services.BookService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/libraryinventory")
public class LibraryContoller {

    public static final Logger LOGGER = LoggerFactory.getLogger(LibraryContoller.class);
    @Autowired
    private BookService bookService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        LOGGER.info("Entering ping API");
        return ResponseEntity.ok("pong");
    }

    @PostMapping("/createNewBook")
    public ResponseEntity<Object> createNewBook(@RequestBody @Valid BookCreateRequest bookCreateRequest) {
        LOGGER.info("Entering createNewBook API");
        try {
            BookResponse newBook = bookService.addNewBook(bookCreateRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/getBooks")
    public ResponseEntity<Object> getBooks(@Valid @RequestBody BookFetchRequest bookFetchRequest) {
        LOGGER.info("Entering getBooks API");
        try {
            var books = bookService.getBooks(bookFetchRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(books);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<Object> updateBook(
            @PathVariable String id,
            @Valid @RequestBody BookUpdateRequest request) {
        LOGGER.info("Entering updateBook API");
        try {
            BookResponse response = bookService.updateBook(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
        }
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable String id) {
        LOGGER.info("Entering deleteBook API");
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok(String.format("Book with bookId: [%s] deleted successfully", id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error occurred");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBooks(
            @RequestParam BookSearchRequest bookSearchRequest
    ) {
        LOGGER.info("Entering searchBooks API");
        Page<BookResponse> results = bookService.searchBooks(bookSearchRequest);
        return ResponseEntity.ok(results);
    }
}
