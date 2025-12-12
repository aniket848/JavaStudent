package com.example.JavaStarter.repository;

import com.example.JavaStarter.DTO.tempDTO;
import com.example.JavaStarter.model.Student;
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


    @Query(value = "SELECT st.* FROM student st , subjects sub WHERE st.student_id = sub.student_id AND sub.subject_name = :subjectName", nativeQuery = true)
    List<Student> getStudentBySubjectName(@Param("subjectName") String subjectName);

    @Query(value = "select stu.student_name as name, stu.class_room as classRoom, count(sub.student_id) as count from student stu , subjects sub \n" +
            "where stu.student_id = :id and \n" +
            "stu.student_id = sub.student_id\n" +
            "group by sub.student_id;",nativeQuery = true)
    tempDTO getSubjectCountbyStudentId(@Param("id") Integer id);

}
