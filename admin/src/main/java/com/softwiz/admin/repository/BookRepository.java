package com.softwiz.admin.repository;

import com.softwiz.admin.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
