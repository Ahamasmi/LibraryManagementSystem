package com.example.LibraryManagementSystem.dto.book;

import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

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

    @AssertTrue(message = "Published year must be between 1600 and the current year")
    public boolean isPublishedYearValid() {
        if (publishedYear == null) return false;
        int currentYear = Year.now().getValue();
        return publishedYear >= 1600 && publishedYear <= currentYear;
    }
}
