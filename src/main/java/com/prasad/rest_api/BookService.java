package com.prasad.rest_api;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
@Scope("request")
public class BookService {
    @Autowired
    private BookRepository bookRepository;


    @Autowired
    private ObjectMapper objectMapper;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book id "+id+"not found"));
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        bookDetails.setAuthor(book.getAuthor());
        bookDetails.setIsbn(book.getIsbn());
        bookDetails.setTitle(book.getTitle());
        return bookRepository.save(book);
    }

    public Book patchUpdateBook(Long id, Map<String, Object> fields) throws JsonMappingException {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found for this id: " + id));

        objectMapper.updateValue(book , fields);
        return bookRepository.save(book);
    }

    public void deleteBook(long id){
        bookRepository.deleteById(id);
    }

}
