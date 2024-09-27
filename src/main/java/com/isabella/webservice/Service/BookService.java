package com.isabella.webservice.Service;

import com.isabella.webservice.Models.Books;
import com.isabella.webservice.Repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;



import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BooksRepository booksRepo;

    public List<Books> getAllBooks() {
        return booksRepo.findAll();
    }

    public Optional<Books> getOneBook(Long id) {
        return Optional.of(booksRepo.findById(id).orElse(new Books()));
    }

    public Books saveBook(Books book) {
        return booksRepo.save(book);
    }

    public Books updateBook(Books book, Long id) {
        Optional<Books> currentBookOpt = booksRepo.findById(id);

        // Kontrollera om boken finns
        if (!currentBookOpt.isPresent()) {
            throw new NoSuchElementException("Boken med ID " + id + " hittades inte");
        }

        Books currentBook = currentBookOpt.get();

        // Uppdatera alla fält med de nya värdena från det givna book-objektet
        currentBook.setTitle(book.getTitle());
        currentBook.setISBN(book.getISBN());

        return booksRepo.save(currentBook);
    }


    public void removeBook(Long id) {
        booksRepo.deleteById(id);
    }

    public void removeBook(Books book) {
        booksRepo.deleteById(book.getId());
    }
}
