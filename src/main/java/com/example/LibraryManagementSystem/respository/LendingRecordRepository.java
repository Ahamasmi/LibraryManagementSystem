package com.example.LibraryManagementSystem.respository;

import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.LendingRecord;
import com.example.LibraryManagementSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LendingRecordRepository extends JpaRepository<LendingRecord, String> {
    Optional<LendingRecord> findByUserAndBookAndActualBorrowEndDateIsNull(User user, Book book);

    @Query("SELECT COUNT(lr) > 0 FROM LendingRecord lr " +
            "WHERE lr.book.id = :bookId AND lr.book.available = true")
    boolean existsByBookIdAndIsAvailable(String bookId);
}