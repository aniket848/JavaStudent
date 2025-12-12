package com.example.JavaStarter.controller;


import com.example.JavaStarter.model.Stream;
import com.example.JavaStarter.model.Student;
import com.example.JavaStarter.model.Subjects;
import com.example.JavaStarter.service.StudentService;
import com.example.JavaStarter.service.clubService;
import com.example.JavaStarter.service.subjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
public class studentController {

    @Autowired
    StudentService studentService;

    @Autowired
    subjectService subjectService;

    @Autowired
    clubService clubService;

    @GetMapping("/")
    public String getData(){
        return "Hello world";
    }

//    @GetMapping("/students")
//    public List<Student> getStudents(){
//        return studentService.getStudent();
//    }

    @PostMapping("/addStudent")
    public String addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    @PutMapping("/updateStudent")
    public String updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }

    @GetMapping("/student")
    public Optional<Student> getStudent(@RequestParam Integer id){
        return studentService.getStudentById(id);
    }

    @DeleteMapping("/student")
    public String deleteStudent(@RequestParam Integer id){
        return studentService.deleteStudent(id);
    }

    @GetMapping("/student/{id}/subjects")
    public ResponseEntity<?> studentSubject(@PathVariable Integer id){
        return subjectService.getSubjects(id);
    }

    @GetMapping("/student/{id}/clubs")
    public ResponseEntity<?> studentClubs(@PathVariable Integer id){
        return clubService.getClubs(id);
    }

    @GetMapping("/clubs/{clubId}/students")
    public ResponseEntity<?> getStudentsByClubId(@PathVariable Integer clubId){
        return studentService.getStudentByClubId(clubId);
    }

    @GetMapping("/subjects/{subjectId}/students")
    public ResponseEntity<?> getStudentsBySubject(@PathVariable Integer subjectId){
        return studentService.getStudentBySubjectId(subjectId);
    }

    @PostMapping("/students/{studentId}/subjects")
    public ResponseEntity<?> addSubjectByStudentId(@RequestBody Subjects subject, @PathVariable Integer studentId){
        return subjectService.addSubject(studentId,subject);
    }

    @GetMapping("/student/search")
    public ResponseEntity<?> getStudentByName(@RequestParam String name){
         return studentService.getStudentByName(name);
    }

    @GetMapping("/student/stream")
    public ResponseEntity<?> getStudentByStream(@RequestParam Stream stream){
        return studentService.getStudentByStream(stream);
    }

    @GetMapping("/student/rollNo")
    public ResponseEntity<?> getStudentsByRollNoRange(@RequestParam Integer min, @RequestParam Integer max){
        return studentService.getStudentByRollNo(min,max);
    }

    @GetMapping("/students")
    public ResponseEntity<Page<Student>> getStudents(Pageable pageable){
       return studentService.getStudentByPagination(pageable);
    }

    @GetMapping("/student/subject-count")
    public ResponseEntity<?> countSubjectPerStudent(){
        return studentService.getStudentBySubjectCount();
    }


    @GetMapping("/student/multi-club")
    public ResponseEntity<?> getStudentsInMultiClub(@RequestParam Integer count){
        return studentService.getStudentsInMultiClub(count);
    }

    @GetMapping("/student/summary/{id}")
    public ResponseEntity<?> getStudentSummary(@PathVariable Integer id){
        return studentService.getStudentSummary(id);
    }

}
