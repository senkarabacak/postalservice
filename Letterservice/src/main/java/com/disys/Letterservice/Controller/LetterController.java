package com.disys.Letterservice.Controller;

import com.disys.Letterservice.Entity.LetterEntity;
import com.disys.Letterservice.Repository.LetterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;


@Service
public class LetterController {

    private final LetterRepo letterRepo;

    @Autowired
    public LetterController(LetterRepo letterRepo) {
        this.letterRepo = letterRepo;
    }


    // This method is annotated with @JmsListener, indicating that it listens to messages from the "letter" destination in ActiveMQ.
    // When a message is received, this method processes it.
    @JmsListener(destination = "letter")
    public void processMessage(String message) {
        System.out.println("Received message: " + message);

        // Attempt to find a letter entity in the database based on the received message (ID).
        LetterEntity letter = letterRepo.findById(convertToInt(message)).orElse(null);
            if (letter != null) {
                letterRepo.updateStatusBasedOnCountry();
        }
    }

    public int convertToInt(String s){
        return Integer.parseInt(s);
    }
}