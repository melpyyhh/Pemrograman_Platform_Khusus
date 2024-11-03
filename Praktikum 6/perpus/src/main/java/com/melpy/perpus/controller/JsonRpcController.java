package com.melpy.perpus.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.melpy.perpus.dto.BookDto;
import com.melpy.perpus.rpc.JsonRpcRequest;
import com.melpy.perpus.rpc.JsonRpcResponse;
import com.melpy.perpus.services.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonRpcController {

    @Autowired
    private BookService bookService;

    @PostMapping("/jsonrpc")
    public ResponseEntity<Object> handleJsonRpcRequest(@RequestBody JsonRpcRequest request) {
        try {
            String method = request.getMethod();
            JsonNode params = request.getParams();
            System.out.println("Method: " + method);
            switch (method) {
                case "createBook":
                    String title = params.get("title").asText();
                    String author = params.get("author").asText();
                    String description = params.get("description").asText();
                    BookDto book = BookDto.builder()
                            .title(title)
                            .author(author)
                            .description(description)
                            .build();

                    bookService.createBook(book);
                    return ResponseEntity.ok(new JsonRpcResponse("created", request.getId()));

                case "getBooks":
                    List<BookDto> books = bookService.getBooks();
                    return ResponseEntity.ok(new JsonRpcResponse(books, request.getId()));

                case "searchBook":
                    String keyword = params.get("keyword").asText();
                    List<BookDto> searchResults = bookService.searchBooksByKeyword(keyword);
                    return ResponseEntity.ok(new JsonRpcResponse(searchResults, request.getId()));

                default:
                    return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
