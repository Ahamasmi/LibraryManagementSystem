package com.example.LibraryManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUpdateRequest {

    private String title;

    private String author;

    private String isbn;

    private Integer publishedYear;

    private Boolean available;
}
