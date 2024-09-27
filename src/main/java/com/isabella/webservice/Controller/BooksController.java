package com.isabella.webservice.Controller;

import com.isabella.webservice.Repository.AuthorRepository;
import com.isabella.webservice.Repository.BooksRepository;
import com.isabella.webservice.Models.Books;
import com.isabella.webservice.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {

    private final BookService bookService;

    //hämta alla
    @GetMapping("")
    public ResponseEntity<List<Books>> getAllBooks() {
        List<Books> books = bookService.getAllBooks();

        return ResponseEntity.ok(books);
    }
    //hämta en bok
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Books>> getOneBook(@PathVariable long id) {
        Optional<Books> book = bookService.getOneBook(id);

        return ResponseEntity.ok(book);
    }
    //skapa ny bok
    @PostMapping("")
    public ResponseEntity<Books> createNewBook(@RequestBody Books newBook) {
        Books book = bookService.saveBook(newBook);

        return ResponseEntity.ok(book);
    }
    //uppdatera bok
    @PutMapping("/{id}")
    public ResponseEntity<Books> updateOneBook(@PathVariable Long id,
                                               @RequestBody Books newBook) {
        Books updatedBook = bookService.updateBook(newBook, id);

        return ResponseEntity.ok(updatedBook);
    }
    //radera bok
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOneBook(@PathVariable long id) {
        bookService.removeBook(id);

        return ResponseEntity.ok("Boken är nu raderad!");
    }
}
