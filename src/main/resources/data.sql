--Prefilling some data for the library management system to play around with.

-- -----------------------------
-- BOOKS (20 real-world books)
-- -----------------------------
INSERT INTO book (id, title, author, isbn, published_year, available, is_deleted) VALUES
('book_01', 'To Kill a Mockingbird', 'Harper Lee', '9780061120084', 1960, true, false),
('book_02', '1984', 'George Orwell', '9780451524935', 1949, true, false),
('book_03', 'Pride and Prejudice', 'Jane Austen', '9780141439518', 1813, true, false),
('book_04', 'The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 1925, false, false), -- ongoing lent
('book_05', 'Moby Dick', 'Herman Melville', '9781503280786', 1851, true, false),
('book_06', 'War and Peace', 'Leo Tolstoy', '9781400079988', 1869, true, false),
('book_07', 'The Catcher in the Rye', 'J.D. Salinger', '9780316769488', 1951, true, false),
('book_08', 'The Hobbit', 'J.R.R. Tolkien', '9780547928227', 1937, true, false),
('book_09', 'Fahrenheit 451', 'Ray Bradbury', '9781451673319', 1953, true, false),
('book_10', 'Jane Eyre', 'Charlotte Brontë', '9780141441146', 1847, true, false),
('book_11', 'The Lord of the Rings', 'J.R.R. Tolkien', '9780544003415', 1954, true, false),
('book_12', 'The Alchemist', 'Paulo Coelho', '9780062315007', 1988, true, false),
('book_13', 'Brave New World', 'Aldous Huxley', '9780060850524', 1932, true, false),
('book_14', 'Animal Farm', 'George Orwell', '9780451526342', 1945, true, false),
('book_15', 'Crime and Punishment', 'Fyodor Dostoevsky', '9780143058144', 1866, true, false),
('book_16', 'The Book Thief', 'Markus Zusak', '9780375842207', 2005, true, false),
('book_17', 'Wuthering Heights', 'Emily Brontë', '9780141439556', 1847, true, false),
('book_18', 'The Kite Runner', 'Khaled Hosseini', '9781594631931', 2003, true, false),
('book_19', 'A Game of Thrones', 'George R. R. Martin', '9780553593716', 1996, false, false), -- ongoing lent
('book_20', 'Dracula', 'Bram Stoker', '9780141439846', 1897, true, true); -- soft-deleted

-- -----------------------------
-- USERS
-- -----------------------------
INSERT INTO library_user (id, name, email, membership_start, membership_end) VALUES
('user_01', 'Alice Johnson', 'alice@example.com', '2022-01-01', '9999-12-31'),
('user_02', 'Bob Smith', 'bob@example.com', '2022-02-01', '9999-12-31'),
('user_03', 'Charlie Lee', 'charlie@example.com', '2022-03-01', '9999-12-31'),
('user_04', 'Dana White', 'dana@example.com', '2022-04-01', '9999-12-31');

-- -----------------------------
-- LENDING RECORDS (7)
-- -----------------------------
-- 3 returned books
INSERT INTO lending_history (id, book_id, user_id, borrow_start_date, expected_borrow_end_date, actual_borrow_end_date) VALUES
('lend_01', 'book_01', 'user_01', '2025-06-01', '2025-06-15', '2025-06-14'),
('lend_02', 'book_02', 'user_02', '2025-05-01', '2025-05-20', '2025-05-18'),
('lend_03', 'book_03', 'user_03', '2025-04-10', '2025-04-25', '2025-04-22'),

-- 2 ongoing books (still lent out, books marked unavailable)
('lend_04', 'book_04', 'user_01', '2025-08-01', '2025-08-15', NULL),
('lend_05', 'book_19', 'user_02', '2025-08-01', '2025-08-20', NULL),

-- 2 more returned
('lend_06', 'book_05', 'user_03', '2025-05-01', '2025-05-15', '2025-05-14'),
('lend_07', 'book_06', 'user_04', '2025-06-01', '2025-06-20', '2025-06-19');

-- -----------------------------
-- WISHLISTS (10)
-- -----------------------------
-- 4 wishlists for ongoing lent books: book_04 and book_19
INSERT INTO wishlist (id, book_id, user_id) VALUES
('wish_01', 'book_04', 'user_02'),
('wish_02', 'book_04', 'user_03'),
('wish_03', 'book_19', 'user_01'),
('wish_04', 'book_19', 'user_03'),

-- 6 wishlists for other available books
('wish_05', 'book_07', 'user_01'),
('wish_06', 'book_08', 'user_02'),
('wish_07', 'book_09', 'user_03'),
('wish_08', 'book_10', 'user_03'),
('wish_09', 'book_11', 'user_01'),
('wish_10', 'book_12', 'user_02');
