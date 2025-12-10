package com.example.JavaStarter.service;


import com.example.JavaStarter.model.Stream;
import com.example.JavaStarter.model.Student;
import com.example.JavaStarter.model.Subjects;
import com.example.JavaStarter.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentRepo studentRepo;

    public String addStudent(Student student){

        student.getSubjects().forEach(sub ->{
            sub.setStudent(student);
        });

        studentRepo.save(student);
        return "Student details are saved.\n" + student;
    }

//    public List<Student> getStudent() {
//        return studentRepo.findAll();
//    }

    public Optional<Student> getStudentById(Integer id) {
       return studentRepo.findById(id);
    }

    public String updateStudent(Student student) {
        studentRepo.save(student);
        return "Student details updated successfully. \n" + student;
    }

    public String deleteStudent(Integer id) {
        studentRepo.deleteById(id);
        return "DELETED SUCCESSFULLY";
    }

    public ResponseEntity<?> getStudentByClubId(Integer clubId) {
       List<Student> studentList = studentRepo.getStudentByClubId(clubId);

       if(studentList.isEmpty()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body("No student found who participated in clubs having id = "+clubId);
       }

       return ResponseEntity.ok(studentList);
    }

    public ResponseEntity<?> getStudentBySubjectId(Integer subjectId) {
       List<Student> stuList = studentRepo.getStudentBySubject(subjectId);

       if(stuList.isEmpty()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No students found with subject Id = "+subjectId);
       }

       return ResponseEntity.ok(stuList);
    }

    public ResponseEntity<?> getStudentByName(String name) {
       List<Student> students = studentRepo.findByNameContainingIgnoreCase(name);

       if(students == null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No student found with name  = "+name);
       }

       return ResponseEntity.status(HttpStatus.OK).body(students);
    }

    public ResponseEntity<?> getStudentByStream(Stream stream) {
       List<Student> students = studentRepo.findBySubjects_Stream(stream);

        if(students == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No student found who are enrolled in stream = "+stream);
        }

        return ResponseEntity.status(HttpStatus.OK).body(students);
    }

    public ResponseEntity<?> getStudentByRollNo(Integer min, Integer max) {

       List<Student> students = studentRepo.findByRollNoBetween(min,max);

       if(students == null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO students found between rollNo "+min +" and "+max);
       }

       return ResponseEntity.status(HttpStatus.OK).body(students);
    }

    public ResponseEntity<Page<Student>> getStudentByPagination(Pageable pageable) {

       Page<Student> studentRes =  studentRepo.findAll(pageable);

       if(studentRes.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(studentRes);
       }

        return ResponseEntity.status(HttpStatus.OK).body(studentRes);
    }
}
