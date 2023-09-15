package com.softwiz.adminms.service;

import com.softwiz.adminms.dto.BookDTO;
import com.softwiz.adminms.entity.AdminUser;
import com.softwiz.adminms.entity.Book;
import com.softwiz.adminms.entity.User;
import com.softwiz.adminms.repository.AdminUserRepository;
import com.softwiz.adminms.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AdminBookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private AdminUserRepository adminUserRepository;

    public Book createBookByAdmin(BookDTO bookDTO, Long adminId) {
        AdminUser createdByAdmin = adminUserRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin user not found"));

        Book existingBook = bookRepository.findByTitle(bookDTO.getTitle());
        if (existingBook != null) {
            throw new IllegalArgumentException("Book with the same title already exists.");
        }
        Book existingBookWithSameISBN = bookRepository.findByIsbn(bookDTO.getIsbn());

        if (existingBookWithSameISBN != null) {
            throw new IllegalArgumentException("Book with the same ISBN already exists.");
        }
        // Convert DTO to Book entity
        Book book = new Book();
        book.setBookId(bookDTO.getBookId());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setGenre(bookDTO.getGenre());
        book.setIsbn(bookDTO.getIsbn());
        book.setIsAvailable(bookDTO.getIsAvailable());
        book.setCreatedByAdmin(createdByAdmin); // Set the admin user who created this user
        // Save the User entity
        bookRepository.save(book);
        auditLogService.addLog(adminId,"Create Book", "Book", book.getBookId());
        return book;
    }
    public void deleteBookByAdmin(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));
        // Perform book deletion
        bookRepository.delete(book);
    }
    public AdminUser findAdminById(Long adminId) {
        return adminUserRepository.findById(adminId).orElse(null);
    }
    public Optional<Book> findBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }
}
