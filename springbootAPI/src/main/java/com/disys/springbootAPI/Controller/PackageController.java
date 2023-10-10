package com.disys.springbootAPI.Controller;



import com.disys.springbootAPI.Entity.PackageEntity;
import com.disys.springbootAPI.Repository.PackageRepo;
import com.disys.springbootAPI.Message.Producer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * POST endpoint to create a package
 * Save the packageEntity to the database, create a new thread to produce the ID as message to "package" queue (from ActiveMQ docs)
 * return created status and saved packageEntity in body
 */

@RequestMapping("/api/packages")
@RestController
@ComponentScan(basePackages = "com.disys.springbootAPI.Repository.PackageRepo")
public class PackageController {


    private PackageRepo packageRepo;

    @Autowired
    public PackageController(PackageRepo packageRepo) {
        this.packageRepo = packageRepo;
    }



    @PostMapping("/package")
    public ResponseEntity<PackageEntity> createPackage(@Valid @RequestBody PackageEntity packageEntity) {
        packageRepo.save(packageEntity);
        thread(new Producer("package", Integer.toString(packageEntity.getId())), false);
        return ResponseEntity.status(HttpStatus.CREATED).body(packageEntity);
    }

    
    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }
}

