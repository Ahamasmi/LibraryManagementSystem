package com.example.LibraryManagementSystem.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "library_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // auto-gen
    private String id;

    @NotBlank(message = "Name can't be blank!")
    private String name;

    @Email(message = "Email must be valid!")
    @Column(unique = true, nullable = false)
    private String email;

    private LocalDate membershipStart;

    private LocalDate membershipEnd;
}
