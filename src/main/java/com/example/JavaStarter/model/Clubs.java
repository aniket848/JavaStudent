package com.example.JavaStarter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clubs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clubId;

    @Column(name = "club_name")
    private String clubName;

    @Column(name = "club_fees")
    private Double clubFees;

    @Column(name = "practice_days")
    private Integer practiceDays;

    @ManyToMany(mappedBy = "clubs")
    @JsonIgnore
    @ToString.Exclude
    private List<Student> student;

//    public void deleteStudent(Integer studentId){
//
//        this.student = this.student.stream()
//                .filter(student-> !student.getStudentId().equals(studentId))
//                .collect(Collectors.toList());
//    }

}
