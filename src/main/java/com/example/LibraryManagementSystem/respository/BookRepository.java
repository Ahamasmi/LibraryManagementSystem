package com.example.LibraryManagementSystem.respository;

import com.example.LibraryManagementSystem.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, String> {
    @Query("SELECT COUNT(b) > 0 FROM Book b " +
            "WHERE :isbn = b.isbn")
    boolean existsByIsbnAll(String isbn);//when ISBN is available

    @Query("SELECT COUNT(b) = 0 FROM Book b " +
            "WHERE :isbn = b.isbn " +
            "AND :id = b.id")
    boolean existsByIsbnAndIdNot(String isbn, String id);//when id is know(updates)

    @Query("SELECT b FROM Book b " +
            "WHERE b.isDeleted = false " +
            "AND (:authors IS NULL OR b.author IN :authors) " +
            "AND (:publishedYears IS NULL OR b.publishedYear IN :publishedYears)")
    Page<Book> findBooksWithFilters(
            @Param("authors") List<String> authors,
            @Param("publishedYears") List<Integer> publishedYears,
            Pageable pageable
    );

    @Query("SELECT b FROM Book b " +
            "WHERE b.isDeleted = false " +
            "AND  :id = b.id ")
    Optional<Book> findByIdActive(String id);

    @Query("SELECT b FROM Book b WHERE b.isDeleted = false AND " +
            "(UPPER(b.title) LIKE UPPER(CONCAT('%', :query, '%')) " +
            "OR UPPER(b.author) LIKE UPPER(CONCAT('%', :query, '%')))")
    Page<Book> searchByTitleOrAuthor(@Param("query") String query, Pageable pageable);

    @Query("SELECT b FROM Book b " +
            "WHERE b.isDeleted = true AND " +
            "b.id=:id ")
    public Optional<Book> findDeletedBookById(String id);
}
