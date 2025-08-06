package com.example.LibraryManagementSystem.respository;

import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.User;
import com.example.LibraryManagementSystem.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, String> {
    boolean existsByUserAndBook(User user, Book book);

    Wishlist getByUserAndBook(User user, Book book);

    List<Wishlist> findAllByBook(Book book);
}