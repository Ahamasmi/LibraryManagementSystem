package com.example.LibraryManagementSystem.dto.book;

import com.example.LibraryManagementSystem.validation.annotation.ValidPublishedYear;
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

    @ValidPublishedYear(isUpdate = true)
    private Integer publishedYear;

    private Boolean available;
}
