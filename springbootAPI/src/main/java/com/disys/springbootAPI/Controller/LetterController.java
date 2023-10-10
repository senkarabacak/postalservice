package com.disys.springbootAPI.Controller;


import com.disys.springbootAPI.Entity.LetterEntity;
import com.disys.springbootAPI.Repository.LetterRepo;
import com.disys.springbootAPI.Message.Producer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



/*
 * POST endpoint to create a letter
 * Save the letterEntity to the database, create a new thread to produce the ID to "letter" as message queue (from ActiveMQ docs)
 * return created status and saved letterEntity in body
 */

@RestController
@RequestMapping("/api/letters")
@ComponentScan(basePackages = "com.disys.springbootAPI.Repository.LetterRepo")

public class LetterController {
    private LetterRepo letterRepo;

    @Autowired
    public LetterController(LetterRepo letterRepo) {
        this.letterRepo = letterRepo;
    }





    @PostMapping("/letter")
    public ResponseEntity<LetterEntity> createLetter(@Valid @RequestBody LetterEntity letterEntity) {
        letterRepo.save(letterEntity);
        thread(new Producer("letter", Integer.toString(letterEntity.getId())), false);
        return ResponseEntity.status(HttpStatus.CREATED).body(letterEntity);
    }

    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }


}
