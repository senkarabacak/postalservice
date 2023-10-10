package com.disys.Packageservice;

import com.disys.Packageservice.Controller.PackageController;
import com.disys.Packageservice.Entity.PackageEntity;
import com.disys.Packageservice.Repository.PackageRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class PackageControllerTest {

    @Mock
    private PackageRepo packageRepo;
    @Mock
    private JmsTemplate jmsTemplate;

    private PackageController packageController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        packageController = new PackageController(packageRepo);
    }

    @Test
    public void testReceiveMessageWithValidMessage() {
        String validMessage = "1"; // Sample valid message

        // Create a mock PackageEntity that you expect to be returned
        PackageEntity expectedPackageEntity = new PackageEntity("Package1", 5.0, "waiting");
        expectedPackageEntity.setId(1);

        // Mock the behavior of findById
        when(packageRepo.findById(anyInt())).thenReturn(Optional.of(expectedPackageEntity));

        // Call the receiveMessage method with the valid message
        packageController.processMessage(validMessage);

        // Verify that findById was called with the correct argument
        verify(packageRepo).findById(1);

        // Verify that updateStatusBasedOnWeight was called
        verify(packageRepo).updateStatusBasedOnWeight();
    }

    @Test
    public void testReceiveMessageWithInvalidMessage() {
        String invalidMessage = "abc"; // Sample invalid message

        // Call the receiveMessage method with the invalid message
        packageController.processMessage(invalidMessage);

        // Verify that findById is never called (since the message is invalid)
        verify(packageRepo, never()).findById(anyInt());

        // Verify that updateStatusBasedOnWeight is never called
        verify(packageRepo, never()).updateStatusBasedOnWeight();
    }

    @Test
    public void testReceiveMessage() {
        // Mock the JMS message
        String message = "1"; // Example message

        // Mock a PackageEntity with the expected ID
        PackageEntity mockPackage = new PackageEntity("Package1", 5.0, "waiting");
        mockPackage.setId(1);

        // Mock the behavior of packageRepo.findById
        when(packageRepo.findById(1)).thenReturn(Optional.of(mockPackage));

        // Call the receiveMessage method
        packageController.processMessage(message);

        // Verify that the findById method of packageRepo was called with the expected ID
        verify(packageRepo, times(1)).findById(1);

        // Verify that updateStatusBasedOnWeight was called once
        verify(packageRepo, times(1)).updateStatusBasedOnWeight();

        // Verify that jmsTemplate.convertAndSend was called once with the expected destination and message
        verify(jmsTemplate, times(1)).convertAndSend("another-destination", "Received message: " + message);
    }

}
