package com.service.student.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.HashMap;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "roll number cannot be null")
    private long rollNumber;

    @NotNull(message = "name cannot be null")
    private String name;

    @NotNull(message = "email cannot be null")
    @Email(message = "invalid email")
    @Column(unique = true)
    private String email;

    @NotNull(message = "contact cannot be null")
    @Length(min = 10, max = 10, message = "invalid contact number")
    @Column(unique = true)
    private String contact;

    private Long coordinator;

    public HashMap<String, Object> studentDetailsAsMap(){
        HashMap<String, Object> map=new HashMap<>();
        map.put("rollNumber",rollNumber);
        map.put("name",name);
        map.put("email",email);
        return map;
    }
}
