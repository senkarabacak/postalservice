package com.disys.springbootAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.disys.springbootAPI.Controller.StatusController;
import com.disys.springbootAPI.Entity.LetterEntity;
import com.disys.springbootAPI.Entity.PackageEntity;
import com.disys.springbootAPI.Entity.StatusEntity;
import com.disys.springbootAPI.Repository.LetterRepo;
import com.disys.springbootAPI.Repository.PackageRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class StatusControllerTest {

    @Mock
    private LetterRepo letterRepo;

    @Mock
    private PackageRepo packageRepo;

    private StatusController statusController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        statusController = new StatusController(letterRepo, packageRepo);
    }

    @Test
    public void testGetStatusInfo() {

        // Create sample data
        List<LetterEntity> letters = new ArrayList<>();

        LetterEntity letterEntity1 = new LetterEntity("Letter1", "US");
        letterEntity1.setId(1);
        letters.add(letterEntity1);
        LetterEntity letterEntity2 =new LetterEntity("Letter2", "CA");
        letterEntity2.setId(2);
        letters.add(letterEntity2);

        List<PackageEntity> packages = new ArrayList<>();

        PackageEntity packageEntity1 = new PackageEntity("Package1", 50.0);
        packageEntity1.setId(1);
        packages.add(packageEntity1);
        PackageEntity packageEntity2 = new PackageEntity("Package2", 75.0);
        packageEntity2.setId(2);
        packages.add(packageEntity2);


        // Mock the behavior of the letter and package repositories
        when(letterRepo.findAll()).thenReturn(letters);
        when(packageRepo.findAll()).thenReturn(packages);

        ResponseEntity<List<StatusEntity>> statusResponse = statusController.getStatusInfo();
        List<StatusEntity> statusEntities = statusResponse.getBody();

        // Verify that the response contains the expected number of elements
        assertEquals(4, statusEntities.size());

        // Verify the data for individual entities in the response
        assertEquals("Letter", statusEntities.get(0).getService());
        assertEquals("Letter1", statusEntities.get(0).getName());
        assertEquals("waiting", statusEntities.get(0).getStatus());

        assertEquals("Letter", statusEntities.get(1).getService());
        assertEquals("Letter2", statusEntities.get(1).getName());
        assertEquals("waiting", statusEntities.get(1).getStatus());

        assertEquals("Package", statusEntities.get(2).getService());
        assertEquals("Package1", statusEntities.get(2).getName());
        assertEquals("waiting", statusEntities.get(2).getStatus());

        assertEquals("Package", statusEntities.get(3).getService());
        assertEquals("Package2", statusEntities.get(3).getName());
        assertEquals("waiting", statusEntities.get(3).getStatus());

        // Verify that the findAll method of both repositories were called
        verify(letterRepo, times(1)).findAll();
        verify(packageRepo, times(1)).findAll();
    }
}