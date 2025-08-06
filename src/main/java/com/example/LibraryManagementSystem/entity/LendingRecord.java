package com.example.LibraryManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "lending_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LendingRecord {
    @NotNull
    public LocalDate borrowStartDate;
    @NotNull
    public LocalDate expectedBorrowEndDate;
    public LocalDate actualBorrowEndDate;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // auto-gen
    private String id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
