package com.example.JavaStarter.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class studentSummary {

    private String name;
    private String classRoom;
    private Long subjectCount;
    private Long clubCount;
}
