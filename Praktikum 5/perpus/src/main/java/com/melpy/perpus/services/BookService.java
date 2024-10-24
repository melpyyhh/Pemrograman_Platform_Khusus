package com.melpy.perpus.services;

import java.util.List;
import com.melpy.perpus.dto.BookDto;

public interface BookService {
    public BookDto createBook(BookDto bookDto);

    public BookDto updateBook(BookDto bookDto);

    public void deleteBook(BookDto bookDto);

    public List<BookDto> getBooks();

    public BookDto getBook(Long id);

    public List<BookDto> searchBooksByKeyword(String keyword);
}
