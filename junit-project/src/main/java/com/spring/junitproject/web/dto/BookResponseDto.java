package com.spring.junitproject.web.dto;

import com.spring.junitproject.domain.Book;

import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Getter
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;

    public BookResponseDto toDto(Book bookPs) {
        this.id = bookPs.getId();
        this.title = bookPs.getTitle();
        this.author = bookPs.getAuthor();
        return this;
    }
}
