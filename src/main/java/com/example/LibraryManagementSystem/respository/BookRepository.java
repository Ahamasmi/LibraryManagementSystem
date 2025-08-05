package com.example.LibraryManagementSystem.respository;

import com.example.LibraryManagementSystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, String> {
    boolean existsByIsbn(String isbn);
}
