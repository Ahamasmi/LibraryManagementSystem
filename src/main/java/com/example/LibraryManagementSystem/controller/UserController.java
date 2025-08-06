package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.user.UserActivityRequest;
import com.example.LibraryManagementSystem.dto.user.UserDTO;
import com.example.LibraryManagementSystem.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static com.example.LibraryManagementSystem.constants.BookServiceConstants.BORROWING_DURATION_DAYS;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    //------------------User Activity APIs------------------
    @PostMapping("/wishlist/add")
    public ResponseEntity<String> addToWishlist(@RequestBody @Valid UserActivityRequest request) {
        LOGGER.info("Entering addToWishlist API");
        try {
            String result = userService.addBookToWishlist(request.getUserId(), request.getBookId());
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/wishlist/remove")
    public ResponseEntity<String> removeFromWishlist(@RequestBody @Valid UserActivityRequest request) {
        LOGGER.info("Entering removeFromWishlist API");
        try {
            String result = userService.removeBookFromWishList(request.getUserId(), request.getBookId());
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/borrowbook")
    public ResponseEntity<String> borrowBook(@RequestBody @Valid UserActivityRequest request) {
        LOGGER.info("Entering borrowBook API");
        try {
            String result = userService.borrowBook(
                    request.getUserId(),
                    request.getBookId(),
                    request.getActivityDate().plusDays(BORROWING_DURATION_DAYS)
            );
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/returnbook")
    public ResponseEntity<String> returnBook(@RequestBody @Valid UserActivityRequest request) {
        LOGGER.info("Entering returnBook API");
        try {
            String result = userService.returnBook(
                    request.getUserId(),
                    request.getBookId(),
                    request.getActivityDate().plusDays(BORROWING_DURATION_DAYS)
            );
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    //------------------User CRUD------------------
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO request) {
        LOGGER.info("Entering createUser API");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String id) {
        LOGGER.info("Entering getUser API");
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        LOGGER.info("Entering getAllUsers API");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id,
                                              @RequestBody @Valid UserDTO dto) {
        LOGGER.info("Entering updateUser API");
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        LOGGER.info("Entering deleteUser API");
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}