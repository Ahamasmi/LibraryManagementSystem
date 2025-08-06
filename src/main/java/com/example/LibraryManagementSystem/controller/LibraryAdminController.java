package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.BookResponse;
import com.example.LibraryManagementSystem.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/admin")
public class LibraryAdminController {
    @Autowired
    private BookService bookService;
    public static final Logger LOGGER = LoggerFactory.getLogger(LibraryAdminController.class);

    @PutMapping("/restockBook/{id}")
    public ResponseEntity<Object> restockBook(
            @PathVariable String id) {
        try {
            BookResponse response = bookService.reStockBook(id);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> hardDeleteBook(
            @PathVariable String id) {
        try {
            bookService.permaDeleteBook(id);
            return ResponseEntity.ok(String.format("Book [%s] permanently deleted successfully.",id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
        }
    }
}
