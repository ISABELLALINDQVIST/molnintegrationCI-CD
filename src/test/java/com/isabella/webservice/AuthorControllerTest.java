package com.isabella.webservice;

import com.isabella.webservice.Controller.AuthorController;
import com.isabella.webservice.Models.Author;
import com.isabella.webservice.Service.AuthorService;
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

public class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    public AuthorControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAuthors() {
        Author author1 = new Author(1L, "Author 1", 45);
        Author author2 = new Author(2L, "Author 2", 50);

        when(authorService.getAllAuthors()).thenReturn(Arrays.asList(author1, author2));

        ResponseEntity<List<Author>> response = authorController.getAllAuthors();
        assertEquals(2, response.getBody().size());
        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    public void testGetOneAuthor() {
        Author author = new Author(1L, "Author 1", 45);

        when(authorService.getOneAuthor(1L)).thenReturn(Optional.of(author));

        ResponseEntity<Optional<Author>> response = authorController.getOneAuthor(1L);
        assertEquals("Author 1", response.getBody().get().getName());
        verify(authorService, times(1)).getOneAuthor(1L);
    }

    @Test
    public void testCreateNewAuthor() {
        Author author = new Author(1L, "New Author", 30);

        when(authorService.saveAuthor(author)).thenReturn(author);

        ResponseEntity<Author> response = authorController.createNewAuthor(author);
        assertEquals("New Author", response.getBody().getName());
        verify(authorService, times(1)).saveAuthor(author);
    }

    @Test
    public void testDeleteOneAuthor() {
        doNothing().when(authorService).removeAuthor(1L);

        ResponseEntity<String> response = authorController.deleteOneAuthor(1L);
        assertEquals("Författaren är nu raderad!", response.getBody());
        verify(authorService, times(1)).removeAuthor(1L);
    }
}
