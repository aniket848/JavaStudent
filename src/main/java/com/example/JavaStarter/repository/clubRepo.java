package com.example.JavaStarter.repository;

import com.example.JavaStarter.DTO.tempDTO;
import com.example.JavaStarter.model.Clubs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface clubRepo extends JpaRepository<Clubs,Integer> {


    @Query(value = "SELECT * from clubs c WHERE c.club_id IN (SELECT sc.club_id from student_club sc WHERE sc.student_id = :id)"
            , nativeQuery = true)
    List<Clubs> getStudentClub(@Param("id") Integer id);


    @Query(value = "SELECT * from clubs c WHERE c.practice_days >= :days",nativeQuery = true)
    List<Clubs> getFilterClubs(@Param("days") Integer days);

    @Query(value = "select stu.student_name as name, stu.class_room as classRoom, count(stc.student_id) as count from student stu , " +
            "student_club stc where stu.student_id = stc.student_id\n" +
            "and stu.student_id= :id group by stc.student_id;",nativeQuery = true)
    tempDTO getClubCountbyStudentId(@Param("id") Integer id);
}
