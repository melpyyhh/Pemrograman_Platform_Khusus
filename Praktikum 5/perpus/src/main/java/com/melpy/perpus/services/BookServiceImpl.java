package com.melpy.perpus.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melpy.perpus.dto.BookDto;
import com.melpy.perpus.entity.Book;
import com.melpy.perpus.mapper.BookMapper;
import com.melpy.perpus.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = bookRepository.save(BookMapper.mapToBook(bookDto));
        return BookMapper.mapToBookDto(book);
    }

    @Override
    public List<BookDto> getBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtos = books.stream()
                .map((product) -> (BookMapper.mapToBookDto(product)))
                .collect(Collectors.toList());
        return bookDtos;
    }

    @Override
    public BookDto getBook(Long id) {
        Book book = bookRepository.getReferenceById(id);
        return BookMapper.mapToBookDto(book);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        Book book = bookRepository.save(BookMapper.mapToBook(bookDto));
        return BookMapper.mapToBookDto(book);
    }

    @Override
    public void deleteBook(BookDto bookDto) {
        bookRepository.delete(BookMapper.mapToBook(bookDto));
    }

    @Override
    public List<BookDto> searchBooksByKeyword(String keyword) {
        List<Book> books = bookRepository.findByTitleContainingOrAuthorContainingOrDescriptionContaining(keyword,
                keyword,
                keyword);
        return books.stream()
                .map(BookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }
}
