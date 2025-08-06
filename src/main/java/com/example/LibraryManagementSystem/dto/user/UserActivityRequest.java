package com.example.LibraryManagementSystem.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserActivityRequest {
    @NotNull
    private String userId;
    @NotNull
    private String bookId;
    private LocalDate activityDate;
}
