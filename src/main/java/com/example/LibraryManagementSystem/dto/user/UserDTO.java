package com.example.LibraryManagementSystem.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String userId;

    @NotBlank(message = "Name can't be blank!")
    private String name;

    @Email(message = "Email must be valid!")
    private String email;

    private LocalDate membershipStart;

    private LocalDate membershipEnd;
}