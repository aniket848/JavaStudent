package com.example.JavaStarter.repository;


import com.example.JavaStarter.DTO.studentSummary;
import com.example.JavaStarter.model.Stream;
import com.example.JavaStarter.model.Student;
import com.example.JavaStarter.DTO.studentSubjectDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @Query(value = "SELECT stu.student_name AS studentName, count(sub.student_id) AS subjectCount \n" +
            "FROM student stu LEFT JOIN\n" +
            "subjects sub ON stu.student_id = sub.student_id\n" +
            "GROUP BY sub.student_id, stu.student_id ORDER BY subjectCount DESC;",nativeQuery = true)
    List<studentSubjectDTO> getSubjectCountPerStudent();

    @Query(value = "SELECT stu.* FROM student stu, student_club stc\n" +
            "            WHERE stu.student_id = stc.student_id GROUP BY stc.student_id HAVING count(stc.student_id) > :count\n" +
            "            ORDER BY stu.student_name ASC; ",nativeQuery = true)
    List<Student> findStudentsHavingMultiClub(@Param("count") Integer count);

}


