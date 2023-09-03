package com.softwiz.adminms.repository;

import com.softwiz.adminms.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
