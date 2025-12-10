package com.example.JavaStarter.repository;


import com.example.JavaStarter.model.Stream;
import com.example.JavaStarter.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {


    @Query(value = "SELECT * from student s WHERE s.student_id IN (SELECT sc.student_id from student_club sc WHERE sc.club_id = :clubId)",
            nativeQuery = true)
    List<Student> getStudentByClubId(@Param("clubId")  Integer clubId);


    @Query(value = "SELECT * from student s WHERE s.student_id IN (SELECT sb.student_id from subjects sb WHERE sb.subject_code = :subjectId)",
            nativeQuery = true)
    List<Student> getStudentBySubject(Integer subjectId);

    List<Student> findByNameContainingIgnoreCase(String name);

    List<Student> findBySubjects_Stream(Stream stream);

    List<Student> findByRollNoBetween(Integer min, Integer max);

}
