package com.disys.Packageservice.Controller;

import com.disys.Packageservice.Entity.PackageEntity;
import com.disys.Packageservice.Repository.PackageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class PackageController {
    private final PackageRepo packageRepo;

    @Autowired
    public PackageController(PackageRepo packageRepo) {
        this.packageRepo = packageRepo;
    }


    // This method is annotated with @JmsListener, indicating that it listens to messages from the "package" destination in ActiveMQ.
    // When a message is received, this method processes it.
    @JmsListener(destination = "package")
    public void processMessage(String message) {
        System.out.println("Received message: " + message);

        // Attempt to find a letter entity in the database based on the received message (ID).
        PackageEntity packageEntity = packageRepo.findById(convertToInt(message)).orElse(null);
        if (packageEntity != null) {
            packageRepo.updateStatusBasedOnWeight();

        }

    }

    public int convertToInt(String s){
        return Integer.parseInt(s);
    }


}
