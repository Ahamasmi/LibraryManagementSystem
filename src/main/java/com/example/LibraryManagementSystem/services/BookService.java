package com.example.LibraryManagementSystem.services;

import com.example.LibraryManagementSystem.config.DefaultConfig;
import com.example.LibraryManagementSystem.dto.book.*;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.respository.BookRepository;
import com.example.LibraryManagementSystem.utils.BookUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private DefaultConfig defaultConfig;
    @Autowired
    private BookRepository bookRepository;

    public BookResponse addNewBook(BookCreateRequest bookCreateRequest) throws IllegalArgumentException {
        if (bookRepository.existsByIsbnAll(bookCreateRequest.getIsbn())) {
            throw new IllegalArgumentException("ISBN already exists");
        }

        Book book = Book.builder()
                .title(bookCreateRequest.getTitle())
                .author(bookCreateRequest.getAuthor())
                .isbn(bookCreateRequest.getIsbn())
                .publishedYear(bookCreateRequest.getPublishedYear())
                .available(bookCreateRequest.getAvailable())
                .isDeleted(false)
                .build();

        Book savedBook = bookRepository.save(book);
        return BookUtils.toBookResponse(savedBook);
    }

    public Page<BookResponse> getBooks(BookFetchRequest bookFetchRequest) {
        Pageable pageable = getPageableFromPageAndPageSize(bookFetchRequest.getPage(), bookFetchRequest.getPageSize());
        Page<Book> books = bookRepository.findBooksWithFilters(bookFetchRequest.getAuthors(), bookFetchRequest.getPublishedYears(), pageable);
        return books.map(BookUtils::toBookResponse);
    }

    public BookResponse updateBook(String id, BookUpdateRequest bookUpdateRequest) {
        Book existingBook = bookRepository.findByIdActive(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // check for duplicate ISBN
        if (bookUpdateRequest.getIsbn() != null &&
                bookRepository.existsByIsbnAndIdNot(bookUpdateRequest.getIsbn(), id)) {
            throw new IllegalArgumentException("Another book with this ISBN already exists");
        }

        // Update fields if present
        if (bookUpdateRequest.getTitle() != null) existingBook.setTitle(bookUpdateRequest.getTitle());
        if (bookUpdateRequest.getAuthor() != null) existingBook.setAuthor(bookUpdateRequest.getAuthor());
        if (bookUpdateRequest.getIsbn() != null) existingBook.setIsbn(bookUpdateRequest.getIsbn());
        if (bookUpdateRequest.getPublishedYear() != null)
            existingBook.setPublishedYear(bookUpdateRequest.getPublishedYear());
        if (bookUpdateRequest.getAvailable() != null) existingBook.setAvailable(bookUpdateRequest.getAvailable());

        Book updatedBook = bookRepository.save(existingBook);
        return BookUtils.toBookResponse(updatedBook);
    }

    public void deleteBook(String id) throws BadRequestException {
        Book book = bookRepository.findByIdActive(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found or already deleted"));
        book.setDeleted(true);
        bookRepository.save(book);
    }

    public void permaDeleteBook(String id) throws BadRequestException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found!"));
        bookRepository.delete(book);
    }

    public BookResponse reStockBook(String id) throws BadRequestException {
        Book book = bookRepository.findDeletedBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("No deleted book found by input book Id"));
        book.setDeleted(false);
        bookRepository.save(book);
        return BookUtils.toBookResponse(book);
    }

    public Page<BookResponse> searchBooks(BookSearchRequest bookSearchRequest) {
        Pageable pageable = getPageableFromPageAndPageSize(bookSearchRequest.getPage(), bookSearchRequest.getPageSize());
        Page<Book> books = bookRepository.searchByTitleOrAuthor(bookSearchRequest.getQuery(), pageable);
        return books.map(BookUtils::toBookResponse);
    }

    private Pageable getPageableFromPageAndPageSize(Integer page, Integer pageSize) {
        return PageRequest.of(
                page != null && page >= 0 ? page : 0,
                pageSize != null && pageSize > 0 ? pageSize : defaultConfig.getPageSize());
    }
}
