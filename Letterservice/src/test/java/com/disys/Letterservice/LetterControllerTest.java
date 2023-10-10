package com.disys.Letterservice;
import com.disys.Letterservice.Controller.LetterController;
import com.disys.Letterservice.Entity.LetterEntity;
import com.disys.Letterservice.Repository.LetterRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class LetterControllerTest {

    @Mock
    private LetterRepo letterRepo;
    @Mock
    private JmsTemplate jmsTemplate;

    private LetterController letterController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        letterController = new LetterController(letterRepo);
    }

    @Test
    public void testProcessMessageWithValidMessage() {
        String validMessage = "123"; // Sample valid message

        // Create a mock LetterEntity that you expect to be returned
        LetterEntity expectedLetterEntity = new LetterEntity("Letter1", "US", "waiting");
        expectedLetterEntity.setId(123);

        // Mock the behavior of findById
        when(letterRepo.findById(anyInt())).thenReturn(Optional.of(expectedLetterEntity));

        // Call the processMessage method with the valid message
        letterController.processMessage(validMessage);

        // Verify that findById was called with the correct argument
        verify(letterRepo).findById(123);

        // Verify that updateStatusBasedOnCountry was called
        verify(letterRepo).updateStatusBasedOnCountry();
    }

    @Test
    public void testProcessMessageWithInvalidMessage() {
        String invalidMessage = "abc"; // Sample invalid message

        // Call the processMessage method with the invalid message
        letterController.processMessage(invalidMessage);

        // Verify that findById is never called (since the message is invalid)
        verify(letterRepo, never()).findById(anyInt());

        // Verify that updateStatusBasedOnCountry is never called
        verify(letterRepo, never()).updateStatusBasedOnCountry();
    }

    @Test
    public void testReceiveMessage() {
        // Mock the JMS message
        String message = "1"; // Example message

        // Mock a PackageEntity with the expected ID
        LetterEntity letterEntity = new LetterEntity("Package1", "AT", "waiting");
        letterEntity.setId(1);

        // Mock the behavior of packageRepo.findById
        when(letterRepo.findById(1)).thenReturn(Optional.of(letterEntity));

        // Call the receiveMessage method
        letterController.processMessage(message);

        // Verify that the findById method of packageRepo was called with the expected ID
        verify(letterRepo, times(1)).findById(1);

        // Verify that updateStatusBasedOnWeight was called once
        verify(letterRepo, times(1)).updateStatusBasedOnCountry();

        // Verify that jmsTemplate.convertAndSend was called once with the expected destination and message
        verify(jmsTemplate, times(1)).convertAndSend("another-destination", "Received message: " + message);
    }
}
