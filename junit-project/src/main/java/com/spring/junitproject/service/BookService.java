package com.spring.junitproject.service;

import java.util.List;
import java.util.Optional;
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
    public BookResponseDto 책한건보기(Long id) {
        Optional<Book> bookOp = bookRepository.findById(id);
        if(bookOp.isPresent()){
            return new BookResponseDto().toDto(bookOp.get());
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }

    // 4. 책삭제
    @Transactional(rollbackFor = RuntimeException.class)
    public void 책삭제하기(Long id){
        bookRepository.deleteById(id);
    }

    // 5. 책수정
    // 4. 책삭제
    @Transactional(rollbackFor = RuntimeException.class)
    public void 책수정하기(Long id, BookSaveReqDto dto){
        Optional<Book> bookOp = bookRepository.findById(id);
        if(bookOp.isPresent()) {
            Book bookPs = bookOp.get();
            bookPs.update(dto.getTitle(), dto.getAuthor());
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
        
    }   // 메서드 종료시에 더티체킹(flush)으로 update가 된다.
}
