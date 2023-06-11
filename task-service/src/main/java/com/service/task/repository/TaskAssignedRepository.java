package com.service.task.repository;


import com.service.task.enums.StatusType;
import com.service.task.model.TaskAssigned;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskAssignedRepository extends JpaRepository<TaskAssigned,Integer> {

    @Query(value = "select student_roll_num from task_assigned where task_id = :taskId",nativeQuery = true)
    public List<Long> getStudentsOfTask(@Param("taskId") Long taskId);

    @Query(value = "select task_id from task_assigned where student_roll_num = :rollNum",nativeQuery = true)
    public List<Long> getTasksOfStudent(@Param("rollNum") Long rollNum);

    @Modifying
    @Transactional
    @Query(value = "delete from task_assigned where student_roll_num = :rollNum",nativeQuery = true)
    public void deleteAllStudentTasks(@Param("rollNum") Long rollNum);

    @Modifying
    @Transactional
    @Query(value = "delete from task_assigned where task_id = :taskId",nativeQuery = true)
    public void deleteTaskById(@Param("taskId") Long taskId);

    @Modifying
    @Transactional
    @Query(value = "delete from task_assigned where task_id = :taskId and student_roll_num = :rollNum",nativeQuery = true)
    public void deleteStudentFromTask(@Param("taskId") Long taskId, @Param("rollNum") Long rollNum);

    @Query(value = "select status from task_assigned where task_id = :taskId and student_roll_num = :rollNum",nativeQuery = true)
    public StatusType checkTaskStatus(@Param("taskId") Long taskId, @Param("rollNum") Long rollNum);

    @Modifying
    @Transactional
    @Query(value = "update task_assigned set status = :status where task_id = :taskId and student_roll_num = :rollNum",nativeQuery = true)
    public void changeTaskStatus(@Param("status") String status, @Param("taskId") Long taskId, @Param("rollNum") Long rollNum);

}
