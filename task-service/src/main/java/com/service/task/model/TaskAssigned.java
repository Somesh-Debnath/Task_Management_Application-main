package com.service.task.model;

import com.service.task.enums.StatusType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskAssigned {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "task Id cannot be null")
    private Long taskId;

    @NotNull(message = "student roll number cannot be null")
    private Long studentRollNum;

    @NotNull(message = "status cannot be null")
    @Enumerated(EnumType.STRING)
    private StatusType status;

//    public TaskAssigned(Long task_id, Long rollNum, StatusType type){
//        taskId=task_id;
//        studentRollNum=rollNum;
//        status=type;
//    }
}
