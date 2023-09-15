package com.softwiz.adminms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Book")

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long bookId;
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "adminId")
    private AdminUser createdByAdmin;
}
