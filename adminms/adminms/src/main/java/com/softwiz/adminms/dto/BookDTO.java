package com.softwiz.adminms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BookDTO {

    private String title;
    private String author;
    private String genre;
    private String isbn;
    private Boolean isAvailable;
    private Long adminId;
    private Long bookId;

    @Override
    public String toString() {
        return "BookDTO{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", isbn='" + isbn + '\'' +
                ", isAvailable=" + isAvailable +
                ", adminId=" + adminId +
                ", bookId=" + bookId +
                '}';
    }
}
