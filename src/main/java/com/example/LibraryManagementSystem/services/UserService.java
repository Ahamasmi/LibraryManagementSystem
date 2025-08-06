package com.example.LibraryManagementSystem.services;

import com.example.LibraryManagementSystem.dto.user.UserDTO;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.LendingRecord;
import com.example.LibraryManagementSystem.entity.User;
import com.example.LibraryManagementSystem.entity.Wishlist;
import com.example.LibraryManagementSystem.respository.BookRepository;
import com.example.LibraryManagementSystem.respository.LendingRecordRepository;
import com.example.LibraryManagementSystem.respository.UserRepository;
import com.example.LibraryManagementSystem.respository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BookRepository bookRepository;
    private final WishlistRepository wishlistRepository;
    private final LendingRecordRepository lendingRecordRepository;
    private final NotificationService notificationService;

    //-----------------User Activities-----------------
    public String addBookToWishlist(String userId, String bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));

        if (wishlistRepository.existsByUserAndBook(user, book)) {
            throw new IllegalArgumentException("Book already in wishlist.");
        }

        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .book(book)
                .build();

        wishlistRepository.save(wishlist);
        return "Book added successfully to wishlist.";
    }

    public String removeBookFromWishList(String userId, String bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));

        if (!wishlistRepository.existsByUserAndBook(user, book)) {
            throw new IllegalArgumentException("Book not in wishlist!");
        }
        Wishlist wishlist = wishlistRepository.getByUserAndBook(user, book);
        wishlistRepository.delete(wishlist);
        return "Book removed successfully to wishlist.";
    }

    public String borrowBook(String userId, String bookId, LocalDate expectedReturnDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));

        if (!book.isAvailable()) {
            throw new IllegalArgumentException("Book is currently not available.");
        }

        if (!lendingRecordRepository.existsByBookIdAndIsAvailable(book.getId())) {
            throw new IllegalArgumentException("Book is already borrowed.");
        }

        book.setAvailable(false);
        bookRepository.save(book);

        LendingRecord record = LendingRecord.builder()
                .book(book)
                .user(user)
                .borrowStartDate(LocalDate.now())
                .expectedBorrowEndDate(expectedReturnDate)
                .build();

        lendingRecordRepository.save(record);
        return "Book borrowed successfully.";
    }

    public String returnBook(String userId, String bookId, LocalDate returnDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));

        LendingRecord record = lendingRecordRepository
                .findByUserAndBookAndActualBorrowEndDateIsNull(user, book)
                .orElseThrow(() -> new NoSuchElementException("No active lending record found."));

        if (record.getBorrowStartDate().isAfter(returnDate))
            throw new IllegalArgumentException("Book return date is before borrowdate");
        record.setActualBorrowEndDate(returnDate);
        lendingRecordRepository.save(record);

        book.setAvailable(true);
        bookRepository.save(book);

        //Trigger async notification for users who wishlisted
        List<Wishlist> wishlists = wishlistRepository.findAllByBook(book);
        if (!wishlists.isEmpty()) {
            notificationService.notifyUsersForAvailableBook(book, wishlists);
        }

        return "Book returned successfully.";
    }


    //-----------------User CRUD-----------------
    public UserDTO createUser(UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .membershipStart(dto.getMembershipStart())
                .membershipEnd(dto.getMembershipEnd())
                .build();

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    public UserDTO getUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        return toResponse(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UserDTO updateUser(String id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));

        if (!user.getEmail().equals(dto.getEmail()) && userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use.");
        }

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setMembershipStart(dto.getMembershipStart());
        user.setMembershipEnd(dto.getMembershipEnd());

        return toResponse(userRepository.save(user));
    }

    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserDTO toResponse(User user) {
        return UserDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .membershipStart(user.getMembershipStart())
                .membershipEnd(user.getMembershipEnd())
                .build();
    }
}
