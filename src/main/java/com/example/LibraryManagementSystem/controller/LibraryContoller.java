package com.example.LibraryManagementSystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/libraryinventory")
public class LibraryContoller {
    public static final Logger LOGGER = LoggerFactory.getLogger(LibraryContoller.class);
    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        LOGGER.info("entering ping api");
        return ResponseEntity.ok("pong");
    }

}
