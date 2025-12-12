package com.example.JavaStarter.controller;

import com.example.JavaStarter.service.clubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clubs")
public class clubController {

    @Autowired
    clubService clubService;

    @PutMapping("/{clubId}/student/{studentId}")
    public ResponseEntity<?> addStudentInExistingClub(@PathVariable Integer clubId, @PathVariable Integer studentId){
       // for ex-> Need to add Swati(existing) in Coding(existing) club
        return clubService.addStudent(clubId,studentId);
    }


    @DeleteMapping("/{clubId}/student/{studentId}")
    public ResponseEntity<?> deleteClubFromStudent(@PathVariable Integer clubId, @PathVariable Integer studentId){
        return clubService.deleteStudent(clubId, studentId);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getClubs(@RequestParam Integer days){
        return clubService.getFilterClubs(days);
    }
}
