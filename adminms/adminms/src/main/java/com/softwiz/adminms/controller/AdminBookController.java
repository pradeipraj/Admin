package com.softwiz.adminms.controller;

import com.softwiz.adminms.entity.Book;
import com.softwiz.adminms.service.AdminBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/books")

public class AdminBookController {
    @Autowired
    private AdminBookService adminBookService;
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return adminBookService.createBook(book);
    }
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        adminBookService.deleteBook(id);
    }
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return adminBookService.updateBook(id, book);
    }
    @GetMapping
    public List<Book> getAllBooks() {
        return adminBookService.getAllBooks();
    }
}
