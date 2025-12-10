package com.example.JavaStarter.service;

import com.example.JavaStarter.model.Clubs;
import com.example.JavaStarter.model.Student;
import com.example.JavaStarter.repository.StudentRepo;
import com.example.JavaStarter.repository.clubRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class clubService {

    @Autowired
    clubRepo clubRepo;
    @Autowired
    StudentRepo studentRepo;

    public ResponseEntity<?> getClubs(Integer id) {
        List<Clubs> clubList =  clubRepo.getStudentClub(id);

        if(clubList.isEmpty()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body("No clubs found for the student Id = "+id);
        }

        return ResponseEntity.ok(clubList);
    }

    public ResponseEntity<?> addStudent(Integer clubId, Integer studentId) {

        // 1-> first find student with student_Id
        Student student = studentRepo.findById(studentId).orElse(null);

        // 2-> Find club with clubId
        Clubs club = clubRepo.findById(clubId).orElse(null);

        if(student == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No student found with student Id = "+ studentId);
        }
        else if(club == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No club found with club Id = "+ clubId);
        }

        student.addClub(club); // It will maintain both owning and inverse side

        Student savedStudent = studentRepo.save(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);

    }

    public ResponseEntity<?> deleteStudent(Integer clubId, Integer studentId) {

        // 1-> first find student with student_Id
        Student student = studentRepo.findById(studentId).orElse(null);

        // 2-> Find club with clubId
        Clubs club = clubRepo.findById(clubId).orElse(null);

        if(student == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No student found with student Id = "+ studentId);
        }
        else if(club == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No club found with club Id = "+ clubId);
        }

        // delete club from student entity
        student.deleteClub(club);

        // delete student from club entity
        //club.deleteStudent(studentId);

        Student savedStudent = studentRepo.save(student);

        return ResponseEntity.status(HttpStatus.OK).body(savedStudent);

    }
}
