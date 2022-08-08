package com.spring.junitproject.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest    // DB와 관련된 컴포넌트만 메모리에 로딩. (Controller / Service는 메모리에 X)
public class BookRepositoryTest {
    
    @Autowired  // DI
    private BookRepository bookRepository;

    // 데이터 준비
    // @BeforeAll  // 테스트 시작 전 한번만 실행
    @BeforeEach // 각 테스트 시작 전에 한번 씩 실행
    public void 데이터준비() {
        String title = "junit";
        String author = "겟인데어";
        Book book = Book.builder()
            .title(title)
            .author(author)
            .build();
        bookRepository.save(book);
    }
    // 트랜잭션 단위
    // [데이터준비() + 책등록] (T1) / [데이터준비() + 책목록보기](T2) .. => 각각 새로운 트랜잭션!

    // 1. 책 등록
    @Test
    public void 책등록_test(){
        // given (데이터 준비)
        String title = "junit5";
        String author = "종현코딩";
        Book book = Book.builder()
            .title(title)
            .author(author)
            .build();
        
        // when (테스트 실행)
        Book bookPS = bookRepository.save(book);

        // then (검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }   // 트랜잭션 종료 (저장된 데이터를 초기화함.)

    // 2. 책 목록보기
    @Test
    public void 책목록보기_test() {
        // given
        String title = "junit";
        String author = "겟인데어";

        // when
        List<Book> bookPs = bookRepository.findAll();

        // then
        assertEquals(title, bookPs.get(0).getTitle());
        assertEquals(author, bookPs.get(0).getAuthor());
    }

    // 3. 책 한 건 보기
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책한건보기_test() {
        // given
        String title = "junit";
        String author = "겟인데어";

        // when
        Book bookPs = bookRepository.findById(1L).get();

        // then
        assertEquals(title, bookPs.getTitle());
        assertEquals(author, bookPs.getAuthor());
    }

    // 4. 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책삭제_test() {
        // given
        Long id = 1L;

        // when
        bookRepository.deleteById(id);

        // then
        Optional<Book> bookPs = bookRepository.findById(id);

        // null일 때 성공
        assertFalse(bookRepository.findById(id).isPresent());
    }

    // 5. 책 수정
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책수정_test() {
        // given
        Long id = 1L;
        String title = "junit5";
        String author = "종현코딩";
        Book book = new Book(id, title, author);

        // when
        Book bookPs = bookRepository.save(book);    // update

        // bookRepository.findAll().stream()
        //     .forEach( b -> {
        //         System.out.println(b.getId());
        //         System.out.println(b.getTitle());
        //         System.out.println(b.getAuthor());
        //         System.out.println("===============");
        //     });
        // System.out.println(bookPs.getId());
        // System.out.println(bookPs.getTitle());
        // then
        assertEquals(id, bookPs.getId());
        assertEquals(title, bookPs.getTitle());
        assertEquals(author, bookPs.getAuthor());
        
    }
}
