package com.melpy.perpus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.melpy.perpus.entity.Book;
import com.melpy.perpus.gen.BookType;
import com.melpy.perpus.gen.CreateBookRequest;
import com.melpy.perpus.gen.CreateBookResponse;
import com.melpy.perpus.gen.GetAllBooksRequest;
import com.melpy.perpus.gen.GetAllBooksResponse;
import com.melpy.perpus.repository.BookRepository;

@Endpoint
public class BookEndPoint {
    private static final String NAMESPACE_URI = "http://perpus.melpy.com/gen";

    @Autowired
    private BookRepository bookRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createBookRequest")
    @ResponsePayload
    public CreateBookResponse createBook(@RequestPayload CreateBookRequest request) {
        Book buku = new Book(null, request.getTitle(), request.getAuthor(), request.getDescription());
        bookRepository.save(buku);

        CreateBookResponse response = new CreateBookResponse();
        response.setId(buku.getId());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllBooksRequest")
    @ResponsePayload
    public GetAllBooksResponse getAllBooks(@RequestPayload GetAllBooksRequest request) {
        List<Book> books = bookRepository.findAll();

        GetAllBooksResponse response = new GetAllBooksResponse();
        for (Book book : books) {
            BookType bookType = new BookType();
            bookType.setId(book.getId());
            bookType.setTitle(book.getTitle());
            bookType.setAuthor(book.getAuthor());
            bookType.setDescription(book.getDescription());

            response.getBookList().add(bookType);
        }

        return response;
    }
}