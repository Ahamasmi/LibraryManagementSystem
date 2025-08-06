package com.example.LibraryManagementSystem.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookSearchRequest {
    String query;

    // Pagination parameters
    private Integer page;
    private Integer pageSize;
}
