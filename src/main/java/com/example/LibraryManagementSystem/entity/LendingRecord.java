package com.example.LibraryManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "lending_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LendingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // auto-gen
    private String id;

    @NotNull
    private String bookId;

    @NotNull
    private String userId;

    @NotNull
    public Date borrowStartDate;

    @NotNull
    public Date expectedBorrowEndDate;

    public Date actualBorrowEndDate;

}
