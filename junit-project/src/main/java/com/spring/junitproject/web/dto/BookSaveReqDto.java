package com.spring.junitproject.web.dto;

import com.spring.junitproject.domain.Book;

import lombok.Setter;

@Setter // Controller에서 Setter가 호출되면서 Dto 값이 채워짐.
public class BookSaveReqDto {
    private String title;
    private String author;

    public Book toEntity(){
        return Book.builder()
            .title(title)
            .author(author)
            .build();
    }
}
