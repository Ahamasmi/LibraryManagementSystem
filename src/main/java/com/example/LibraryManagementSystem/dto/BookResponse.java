package com.example.LibraryManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

    private String id;

    private String title;

    private String author;

    private String isbn;

    private Integer publishedYear;

    private boolean available;
}