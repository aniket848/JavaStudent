package com.example.JavaStarter.repository;

import com.example.JavaStarter.model.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepo extends JpaRepository<Subjects,Integer> {


     @Query(value = "SELECT * from subjects s WHERE s.student_id = :id" , nativeQuery = true)
    List<Subjects> getSubjectsByStudentId(@Param("id") Integer id);
}
