package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.BookCreateRequest;
import com.example.LibraryManagementSystem.services.BookService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/libraryinventory")
public class LibraryContoller {

    @Autowired
    private BookService bookService;
    public static final Logger LOGGER = LoggerFactory.getLogger(LibraryContoller.class);
    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        LOGGER.info("entering ping api");
        return ResponseEntity.ok("pong");
    }

    @PostMapping("/createNewBook")
    public ResponseEntity<String> ping(@Valid @RequestBody BookCreateRequest bookCreateRequest){
        LOGGER.info("entering create api");
        try {
            String bookId = bookService.addNewBook(bookCreateRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookId);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
