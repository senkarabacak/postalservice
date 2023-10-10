package com.disys.springbootAPI.Controller;


import com.disys.springbootAPI.Entity.LetterEntity;
import com.disys.springbootAPI.Entity.PackageEntity;
import com.disys.springbootAPI.Entity.StatusEntity;
import com.disys.springbootAPI.Repository.LetterRepo;
import com.disys.springbootAPI.Repository.PackageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/*
 * GET endpoint to retrieve status information
 * Retrieve a list of all letters and packages from the repositories
 * and store them in list. Return a response with the combined status information in the body
 */


@RestController
@RequestMapping("/api/status")
public class StatusController {

    private final LetterRepo letterRepo;
    private final PackageRepo packageRepo;


    @Autowired
    public StatusController(LetterRepo letterRepo, PackageRepo packageRepo) {
        this.letterRepo = letterRepo;
        this.packageRepo = packageRepo;
    }
    @GetMapping
    public ResponseEntity<List<StatusEntity>> getStatusInfo() {
        List<LetterEntity> letters = (List<LetterEntity>) letterRepo.findAll();
        List<PackageEntity> packages = (List<PackageEntity>) packageRepo.findAll();


        List<StatusEntity> allInfoList = new ArrayList<>();

        for (LetterEntity letter : letters) {
            allInfoList.add(new StatusEntity("Letter",letter.getId(), letter.getName(), letter.getStatus()));
        }

        for (PackageEntity packageObj : packages) {
            allInfoList.add(new StatusEntity("Package", packageObj.getId(),packageObj.getName(), packageObj.getStatus()));
        }

        return ResponseEntity.ok(allInfoList);
    }

}
