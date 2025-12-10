package com.example.JavaStarter.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subjects {

    @Column(name="subject_code")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subjectCode;

    @Column(name="subject_name")
    private String subjectName;

    @Column(name="faculty_name")
    private String facultyName;

    @ManyToOne
    @JoinColumn(name="student_id")
    //@JsonBackReference
    @JsonIgnore
    //@ToString.Exclude
    private Student student;

    @Enumerated(EnumType.STRING) //To tell JPA so that it will store enum string instead of ordinal in database
    @Column(name="stream")
    private Stream stream;

    public void deleteStudent(){
        this.setStudent(null);
    }

}
