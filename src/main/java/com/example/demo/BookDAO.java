
package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final org.springframework.jdbc.core.RowMapper<Book> rowMapper =
        (rs, rowNum) -> new Book(
            rs.getLong("book_id"),
            rs.getString("book_name"),
            rs.getString("author"),
            rs.getDouble("price"),
            rs.getBoolean("available")
        );

    public List<Book> getAllBooks() {
        return jdbcTemplate.query(
            "SELECT * FROM books ORDER BY book_id",
            rowMapper
        );
    }

    public Optional<Book> getBookById(Long id) {
        List<Book> books = jdbcTemplate.query(
            "SELECT * FROM books WHERE book_id = ?",
            rowMapper,
            id
        );

        return books.stream().findFirst();
    }

    public Book addBook(Book book) {
        String sql = """
            INSERT INTO books (book_name, author, price, available)
            VALUES (?, ?, ?, ?)
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, book.getBookName());
            ps.setString(2, book.getAuthor());
            ps.setDouble(3, book.getPrice());
            ps.setBoolean(4,
                book.getAvailable() == null || book.getAvailable());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();

        if (key != null) {
            book.setBookId(key.longValue());
        }

        if (book.getAvailable() == null) {
            book.setAvailable(true);
        }

        return getBookById(book.getBookId()).orElse(book);
    }

    public boolean updateBook(Long id, Book book) {
        int rows = jdbcTemplate.update(
            """
            UPDATE books
            SET book_name = ?, author = ?, price = ?, available = ?
            WHERE book_id = ?
            """,
            book.getBookName(),
            book.getAuthor(),
            book.getPrice(),
            book.getAvailable() == null || book.getAvailable(),
            id
        );

        return rows > 0;
    }

    public boolean updateAvailability(Long id, Boolean available) {
        int rows = jdbcTemplate.update(
            "UPDATE books SET available = ? WHERE book_id = ?",
            available, id
        );

        return rows > 0;
    }

    public boolean deleteBook(Long id) {
        return jdbcTemplate.update(
            "DELETE FROM books WHERE book_id = ?", id
        ) > 0;
    }
}
