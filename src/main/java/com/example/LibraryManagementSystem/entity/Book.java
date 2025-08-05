package com.example.LibraryManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


@Entity
@Table(name = "book", uniqueConstraints = @UniqueConstraint(columnNames = "isbn"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    private String isbn;

    //todo: add better validations
    @Min(1600)
    @Max(2025)
    private Integer publishedYear;

    @NotNull
    private boolean available;
}
