package com.service.teacher.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeId;

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
}
