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

    //hämta alla endpoint
    @GetMapping("")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    //hämta författare på id
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Author>> getOneAuthor(@PathVariable long id) {
        Optional<Author> author = authorService.getOneAuthor(id);
        return ResponseEntity.ok(author);
    }

    //skapa ny författare
    @PostMapping("")
    public ResponseEntity<Author> createNewAuthor(@RequestBody Author newAuthor) {
        Author author = authorService.saveAuthor(newAuthor);
        return ResponseEntity.ok(author);
    }

    //uppdatera författare
    @PatchMapping("/{id}")
    public ResponseEntity<Author> updateOneAuthor(@PathVariable Long id, @RequestBody Author newAuthor) {
        Author patchedAuthor = authorService.patchAuthor(newAuthor, id);
        return ResponseEntity.ok(patchedAuthor);
    }

    //radera på id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOneAuthor(@PathVariable long id) {
        authorService.removeAuthor(id);
        return ResponseEntity.ok("Author removed successfully!");
    }
}

