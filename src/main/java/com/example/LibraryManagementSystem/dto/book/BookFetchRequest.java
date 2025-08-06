package com.example.LibraryManagementSystem.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookFetchRequest {
    private List<String> authors;
    private List<Integer> publishedYears;

    // Pagination parameters
    private Integer page;
    private Integer pageSize;
}
