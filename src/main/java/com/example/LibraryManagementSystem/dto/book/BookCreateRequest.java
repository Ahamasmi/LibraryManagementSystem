package com.example.LibraryManagementSystem.dto.book;

import com.example.LibraryManagementSystem.validation.annotation.ValidPublishedYear;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCreateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String isbn;

    @NotNull
    @ValidPublishedYear
    private Integer publishedYear;

    @NotNull
    private Boolean available;
}
