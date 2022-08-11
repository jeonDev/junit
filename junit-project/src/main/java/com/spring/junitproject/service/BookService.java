package com.spring.junitproject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.junitproject.domain.Book;
import com.spring.junitproject.domain.BookRepository;
import com.spring.junitproject.web.dto.BookResponseDto;
import com.spring.junitproject.web.dto.BookSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    // 1. 책 등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResponseDto 책등록하기(BookSaveReqDto dto) {
        Book bookPs = bookRepository.save(dto.toEntity());
        return new BookResponseDto().toDto(bookPs);
    }
    

    // 2. 책목록보기
    public List<BookResponseDto> 책목록보기() {
        return bookRepository.findAll().stream()
                .map(new BookResponseDto()::toDto)
                .collect(Collectors.toList());
            // findAll -> Book Return
    }
    // 3. 책 한건 보기

    // 4. 책삭제

    // 5. 책수정
}
