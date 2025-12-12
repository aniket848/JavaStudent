package com.example.JavaStarter.service;

import com.example.JavaStarter.model.Student;
import com.example.JavaStarter.model.Subjects;
import com.example.JavaStarter.repository.StudentRepo;
import com.example.JavaStarter.repository.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class subjectService {

    @Autowired
    SubjectRepo subjectRepo;

    @Autowired
    StudentRepo studentRepo;

    public ResponseEntity<?> getSubjects(Integer id) {
       List<Subjects> subList =  subjectRepo.getSubjectsByStudentId(id);

       if(subList.isEmpty()){
           //throw new ResourceNotFound("No subjects found for the student Id: "+id );
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body("No subjects found for student Id: "+id);
       }

       return ResponseEntity.ok(subList);
    }

    public ResponseEntity<?> addSubject(Integer studentId, Subjects subject) {

         // find student
        Student student = studentRepo.findById(studentId).orElse(null);

        if(student == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("NO Student found with student Id = "+studentId);
        }

        // Attach student with subject
        subject.setStudent(student);

        // save the subject
        Subjects savedSubject =  subjectRepo.save(subject);

        //return the savedSubject with success message
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubject);

    }

    public ResponseEntity<?> deleteSubjectFromStudent(Integer subjectId) {

        // 1- find subject from subjectId
        Subjects subject = subjectRepo.findById(subjectId).orElse(null);

        if(subject == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subject not found with subject Id = "+subjectId);
        }

        subject.deleteStudent();

        Subjects updatedSubject = subjectRepo.save(subject);

        return ResponseEntity.status(HttpStatus.OK).body(updatedSubject);

    }

    public ResponseEntity<?> deleteSubject(Integer subjectId) {

        if(!subjectRepo.existsById(subjectId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subject Not found");
        }

        subjectRepo.deleteById(subjectId);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully Deleted");
    }

    public ResponseEntity<?> getStudentBySubjectName(String subjectName) {

       List<Student> studentList = subjectRepo.getStudentBySubjectName(subjectName);
       if(studentList.isEmpty()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO student study subject named "+subjectName);

       }

       return ResponseEntity.status(HttpStatus.OK).body(studentList);
    }
}
