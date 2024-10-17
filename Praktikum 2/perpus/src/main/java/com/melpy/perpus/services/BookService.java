package com.melpy.perpus.services;

import java.util.List;
import com.melpy.perpus.dto.BookDto;

public interface BookService {
    public void createBook(BookDto bookDto);

    public List<BookDto> getBooks();

    public List<BookDto> searchBooksByKeyword(String keyword);
}
