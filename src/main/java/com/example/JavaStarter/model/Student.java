package com.example.JavaStarter.model;

import com.example.JavaStarter.converter.StudentConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="student_id")
    private Integer studentId;

    @Column(name="student_name")
    private String name;

    @Column(name="roll_no")
    private Long rollNo;

    @Column(name="class_room")
    private String classRoom;

//    @Column(name="hobbies")
//    @Convert(converter = StudentConverter.class)
//    private List<String> hobbies;

    @ElementCollection
    @CollectionTable(name="student_hobbies", joinColumns = @JoinColumn(name="student_id"))
    @Column(name="hobbies")
    private List<String> hobbies;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL) // Student is a parent table, so mappedBy used in parent table to point column of child table
    @ToString.Exclude // to resolve infinite loop error due to toString()
    //@JsonIgnore
    private List<Subjects> subjects;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="student_club",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "club_id")
    )
    //@JsonIgnore
    private List<Clubs> clubs;

    public void addClub(Clubs club){
        this.clubs.add(club);
        club.getStudent().add(this);
    }

    public void deleteClub(Clubs club){
//        this.clubs = this.clubs.stream()
//                .filter(club -> !club.getClubId().equals(clubId))
//                .collect(Collectors.toList());

        this.clubs.remove(club);
        club.getStudent().remove(this);

    }


    // cascade-> so that if any change occur in parent table, same will reflect in child table, like save, update, delete
}
