package com.isabella.webservice.Service;

import com.isabella.webservice.Models.Author;
import com.isabella.webservice.Repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorService {

    private final AuthorRepository authorRepo;

    //hämta alla författare
    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }

    //hämta författare på ID
    public Optional<Author> getOneAuthor(Long id) {
        return Optional.of(authorRepo.findById(id).orElse(new Author()));
    }

    //skapa eller spara författare
    public Author saveAuthor(Author author) {
        return authorRepo.save(author);
    }

    //uppdatera eller redigera författare
    public Author patchAuthor(Author author, Long id) {
        Optional<Author> currentAuthor = authorRepo.findById(id);

        // Check if fields are different and update accordingly
        if (!author.getName().equals(currentAuthor.get().getName())) currentAuthor.get().setName(author.getName());
        if (author.getAge() != currentAuthor.get().getAge()) currentAuthor.get().setAge(author.getAge());

        return authorRepo.save(currentAuthor.get());
    }

    //radera författade på ID
    public void removeAuthor(Long id) {
        authorRepo.deleteById(id);
    }
}

