package com.isabella.webservice.Service;

import com.isabella.webservice.Models.Author;
import com.isabella.webservice.Repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public Author updateAuthor(Author author, Long id) {
        //hämta den aktuella författaren från databasen
        Optional<Author> currentAuthorOpt = authorRepo.findById(id);

        //kontrollera om författaren finns
        if (!currentAuthorOpt.isPresent()) {
            throw new EntityNotFoundException("Författaren med ID " + id + " hittades inte");
        }

        //hämta den aktuella författaren
        Author currentAuthor = currentAuthorOpt.get();

        //uppdatera alla fält med de nya värdena från det givna author-objektet
        currentAuthor.setName(author.getName());
        currentAuthor.setAge(author.getAge());

        //spara den uppdaterade författaren tillbaka till databasen
        return authorRepo.save(currentAuthor);
    }



    //radera författade på ID
    public void removeAuthor(Long id) {
        authorRepo.deleteById(id);
    }
}

