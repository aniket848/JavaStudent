package com.example.JavaStarter.controller;


import com.example.JavaStarter.service.subjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/subject")
public class subjectController {

    @Autowired
    subjectService subjectService;

    @DeleteMapping("/{subjectId}/student")
    public ResponseEntity<?> deleteStudentForSubject(@PathVariable Integer subjectId){

        return subjectService.deleteSubjectFromStudent(subjectId);
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<?> deleteStudent(@PathVariable Integer subjectId){
        return subjectService.deleteSubject(subjectId);
    }
}
