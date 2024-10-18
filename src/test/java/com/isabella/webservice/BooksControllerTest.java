package com.isabella.webservice;

import com.isabella.webservice.Controller.BooksController;
import com.isabella.webservice.Models.Books;
import com.isabella.webservice.Service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BooksControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BooksController booksController;

    public BooksControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks() {
        Books book1 = new Books(1L, "Book 1", "123456", null);
        Books book2 = new Books(2L, "Book 2", "654321", null);

        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

        ResponseEntity<List<Books>> response = booksController.getAllBooks();
        assertEquals(2, response.getBody().size());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void testGetOneBook() {
        Books book = new Books(1L, "Book 1", "123456", null);

        when(bookService.getOneBook(1L)).thenReturn(Optional.of(book));

        ResponseEntity<Optional<Books>> response = booksController.getOneBook(1L);
        assertEquals("Book 1", response.getBody().get().getTitle());
        verify(bookService, times(1)).getOneBook(1L);
    }

    @Test
    public void testCreateNewBook() {
        Books book = new Books(1L, "New Book", "123456", null);

        when(bookService.saveBook(book)).thenReturn(book);

        ResponseEntity<Books> response = booksController.createNewBook(book);
        assertEquals("New Book", response.getBody().getTitle());
        verify(bookService, times(1)).saveBook(book);
    }

    @Test
    public void testDeleteOneBook() {
        doNothing().when(bookService).removeBook(1L);

        ResponseEntity<String> response = booksController.deleteOneBook(1L);
        assertEquals("Boken är nu raderad!", response.getBody());
        verify(bookService, times(1)).removeBook(1L);
    }
}

