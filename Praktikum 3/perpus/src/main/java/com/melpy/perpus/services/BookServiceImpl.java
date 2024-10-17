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
    private BookRepository bookRepo;

    @Override
    public void createBook(BookDto bookDto) {
        bookRepo.save(BookMapper.mapToBook(bookDto));
    }

    @Override
    public List<BookDto> getBooks() {
        List<Book> books = bookRepo.findAll();
        List<BookDto> bookDtos = books.stream()
                .map((product) -> (BookMapper.mapToBookDto(product)))
                .collect(Collectors.toList());
        return bookDtos;
    }

    @Override
    public List<BookDto> searchBooksByKeyword(String keyword) {
        List<Book> books = bookRepo.findByTitleContainingOrAuthorContainingOrDescriptionContaining(keyword, keyword,
                keyword);
        return books.stream()
                .map(BookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }
}
