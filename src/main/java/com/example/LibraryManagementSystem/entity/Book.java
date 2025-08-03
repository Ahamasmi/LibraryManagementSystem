package com.example.LibraryManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.ElementType;


@Entity
@Table(name = "book")
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
    private boolean isAvailable;

}
