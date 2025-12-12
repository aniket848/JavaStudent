package com.example.JavaStarter.service;


import com.example.JavaStarter.DTO.tempDTO;
import com.example.JavaStarter.model.Stream;
import com.example.JavaStarter.model.Student;
import com.example.JavaStarter.DTO.studentSubjectDTO;
import com.example.JavaStarter.repository.StudentRepo;
import com.example.JavaStarter.repository.SubjectRepo;
import com.example.JavaStarter.repository.clubRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.JavaStarter.DTO.studentSummary;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentRepo studentRepo;
    @Autowired
    SubjectRepo subjectRepo;
    @Autowired
    clubRepo clubRepo;

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

    public ResponseEntity<?> getStudentBySubjectCount() {

        List<studentSubjectDTO> list = studentRepo.getSubjectCountPerStudent();

        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO students found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    public ResponseEntity<?> getStudentsInMultiClub(Integer count) {

        List<Student> StudentList = studentRepo.findStudentsHavingMultiClub(count);

        if(StudentList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO student has registered in more than one clubs");
        }

        return ResponseEntity.status(HttpStatus.OK).body(StudentList);
    }

    public ResponseEntity<?> getStudentSummary(Integer id) {

        tempDTO studentDetailWithSubjectCount = subjectRepo.getSubjectCountbyStudentId(id);
        tempDTO studentDetailWithClubCount = clubRepo.getClubCountbyStudentId(id);

        if(studentDetailWithSubjectCount == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No student found with id = "+id);
        }

        studentSummary studentSummary = new studentSummary();

        studentSummary.setName(studentDetailWithSubjectCount.getName());
        studentSummary.setClassRoom(studentDetailWithSubjectCount.getClassRoom());
        studentSummary.setSubjectCount(studentDetailWithSubjectCount.getCount());
        studentSummary.setClubCount(studentDetailWithClubCount!=null ? studentDetailWithClubCount.getCount() :0);


        return ResponseEntity.status(HttpStatus.OK).body(studentSummary);
    }
}
