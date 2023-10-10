package com.disys.springbootAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.disys.springbootAPI.Controller.PackageController;
import com.disys.springbootAPI.Entity.PackageEntity;
import com.disys.springbootAPI.Repository.PackageRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PackageControllerTest {

    @Mock
    private PackageRepo packageRepo;

    private PackageController packageController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        packageController = new PackageController(packageRepo);
    }

    @Test
    public void testCreatePackage() {

        PackageEntity packageEntity = new PackageEntity("Sample Package", 100.0);
        packageEntity.setId(1);

        // Mock the behavior of the package repository
        when(packageRepo.save(any(PackageEntity.class))).thenReturn(packageEntity);


        ResponseEntity<PackageEntity> createdPackageResponse = packageController.createPackage(packageEntity);
        PackageEntity createdPackage = createdPackageResponse.getBody();
        assertEquals(HttpStatus.CREATED, createdPackageResponse.getStatusCode());
        assertEquals("Sample Package", createdPackage.getName());
        assertEquals(100.0, createdPackage.getWeight());
        assertEquals("waiting", createdPackage.getStatus());

        // Verify that the save method of the repository was called with the packageEntity
        verify(packageRepo, times(1)).save(eq(packageEntity));
    }
}