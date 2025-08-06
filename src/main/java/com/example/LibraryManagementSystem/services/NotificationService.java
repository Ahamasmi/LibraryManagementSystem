package com.example.LibraryManagementSystem.services;

import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.User;
import com.example.LibraryManagementSystem.entity.Wishlist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NotificationService {

    @Async
    public void notifyUsersForAvailableBook(Book book, List<Wishlist> wishlists) {
        for (Wishlist wishlist : wishlists) {
            User user = wishlist.getUser();
            log.info("Notification prepared for {}: Book [{}] is now available.",
                    user.getId(), book.getTitle());
        }
    }
}