package com.disys.springbootAPI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.disys.springbootAPI.Controller.LetterController;
import com.disys.springbootAPI.Entity.LetterEntity;
import com.disys.springbootAPI.Repository.LetterRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class LetterControllerTest {

    @Mock
    private LetterRepo letterRepo;

    private LetterController letterController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        letterController = new LetterController(letterRepo);
    }

    @Test
    public void testCreateLetter() {

        LetterEntity letterEntity = new LetterEntity("Sample Letter", "AT");
        letterEntity.setId(1);

        when(letterRepo.save(any(LetterEntity.class))).thenReturn(letterEntity);

        ResponseEntity<LetterEntity> responseEntity = letterController.createLetter(letterEntity);
        LetterEntity createdLetter = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("AT", createdLetter.getCountry());
        assertEquals("waiting", createdLetter.getStatus());

        // Verify that the save method of the repository was called with the letterEntity
        verify(letterRepo, times(1)).save(eq(letterEntity));
    }
}