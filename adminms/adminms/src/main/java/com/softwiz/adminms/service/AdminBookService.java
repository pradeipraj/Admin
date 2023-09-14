package com.softwiz.adminms.service;

import com.softwiz.adminms.entity.Book;
import com.softwiz.adminms.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminBookService {
    @Autowired
    private BookRepository bookRepository;
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

/*    public Book updateBook(Long bookId, Book book) {
// Implementation for updating book
// Retrieve existing book, update fields, save back
    }*/

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book updateBook(Long bookId, Book book) {
        Optional<Book> existingBookOptional = bookRepository.findById(bookId);
        if (existingBookOptional.isPresent()){
            Book existingBook = existingBookOptional.get();
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setGenre(book.getGenre());
            existingBook.setIsbn(book.getIsbn());
            existingBook.setIsAvailable(book.getIsAvailable());

            return bookRepository.save(existingBook);
        }else {
            throw new IllegalArgumentException("Book not found");
        }
    }
}
