package com.isabella.webservice.Controller;

import com.isabella.webservice.Models.Author;
import com.isabella.webservice.Service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    // Hämta alla författare
    @GetMapping("")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    // Hämta författare med ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Author>> getOneAuthor(@PathVariable long id) {
        Optional<Author> author = authorService.getOneAuthor(id);
        return ResponseEntity.ok(author);
    }

    // Skapa ny författare
    @PostMapping("")
    public ResponseEntity<Author> createNewAuthor(@RequestBody Author newAuthor) {
        Author author = authorService.saveAuthor(newAuthor);
        return ResponseEntity.ok(author);
    }

    // Uppdatera författare med ID
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateOneAuthor(@PathVariable Long id, @RequestBody Author newAuthor) {
        Author updatedAuthor = authorService.updateAuthor(newAuthor, id);
        return ResponseEntity.ok(updatedAuthor);
    }

    // Radera författare med ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOneAuthor(@PathVariable long id) {
        authorService.removeAuthor(id);
        return ResponseEntity.ok("Författaren är nu raderad!");
    }
}
