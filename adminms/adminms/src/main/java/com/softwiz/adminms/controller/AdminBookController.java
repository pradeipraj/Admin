package com.softwiz.adminms.controller;

import com.softwiz.adminms.dto.BookDTO;
import com.softwiz.adminms.entity.AdminUser;
import com.softwiz.adminms.entity.Book;
import com.softwiz.adminms.service.AdminBookService;
import com.softwiz.adminms.service.AdminUserService;
import com.softwiz.adminms.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/books")

public class AdminBookController {
    @Autowired
    private AdminBookService adminBookService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AuditLogService auditLogService;

    @PostMapping("/addBooks")

    public ResponseEntity<?> createBookByAdmin(@RequestBody BookDTO bookDTO) {
        try {
            Long adminId = bookDTO.getAdminId();
            if (adminId == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Book createdBook = adminBookService.createBookByAdmin(bookDTO, adminId);
            return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteBookByAdmin/{adminId}/{bookId}")
    public ResponseEntity<String> deleteBookByAdmin(@PathVariable Long adminId, @PathVariable Long bookId) {
        try {
            // Check if the admin exists
            AdminUser adminUser = adminBookService.findAdminById(adminId);
            if (adminUser == null) {
                return new ResponseEntity<>("Admin not found", HttpStatus.NOT_FOUND);
            }
            // Check if the user exists
            Optional<Book> book = adminBookService.findBookById(bookId);
            if (book.isEmpty()) {
                return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
            }
            // Perform user deletion
            adminBookService.deleteBookByAdmin(bookId);
            auditLogService.addLog(adminId, "Delete Book", "Book", bookId);
            return ResponseEntity.ok("Book deleted successfully");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public List<Book> getAllBooks() {
        return adminBookService.getAllBooks();
    }
    @PutMapping("/updateBookByAdmin/{adminId}/{bookId}")
    public ResponseEntity<?> updateBookDetailsByAdmin(@PathVariable Long adminId, @PathVariable Long bookId,
            @RequestBody BookDTO updatedBookDTO) {
        try {
            // Update the book details
            Book updatedBook = adminBookService.updateBookDetails(adminId, bookId, updatedBookDTO);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
